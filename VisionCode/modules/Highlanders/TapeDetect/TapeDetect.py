import libjevois as jevois
import cv2
import numpy as np
import json
import math


class TapeDetect:
# ###################################################################################################
## Constructor


    def __init__(self):
        # Instantiate a JeVois Timer to measure our processing framerate:
        self.timer = jevois.Timer("processing timer", 100, jevois.LOG_INFO)
        
        self.draw = True
        
        global stringForHSV
        
        self.stringForHSV = "hi"
                
    # ###################################################################################################
    ## Process function with USB output
    def process(self, inframe, outframe):
        #out = self.UniversalProcess(inframe)
        #outframe.sendCv(out)
        #jevois.sendSerial("hello")
        out = self.UniversalProcess(inframe)
        outframe.sendCv(out)

    def processNoUSB(self, inframe):
        out = self.UniversalProcess(inframe)
        #outframe.sendCv(out)
        #jevois.sendSerial("bonjour")
        
    def parseSerial(self, string):
        global stringForHSV
        self.stringForHSV = string
        #jevois.sendSerial("hello parseSerial")
        #jevois.sendSerial(stringForHSV)
        return self.stringForHSV;
    #stringForHSV = string    
        
    def sortContours(self, cntArray):
        arraySize = len(cntArray)
        #jevois.sendSerial(str(arraySize))
        if arraySize == 0:
            return []
            

        sortedArray = [cntArray[0]]
        for i in range(1, arraySize):
            
            rectangle = cv2.minAreaRect(cntArray[i])
            
            for j in range(len(sortedArray)):
                sortedRect = cv2.minAreaRect(sortedArray[j])
                if rectangle[0][1] <= sortedRect[0][1]:
                    sortedArray.insert(j, cntArray[i])
                    break
                if j == (len(sortedArray) - 1):
                    sortedArray.append(cntArray[i])
        
        
        return sortedArray

    def UniversalProcess(self, inframe):
        inimg = inframe.getCvBGR()
        outimg = inimg
        
        #change to hsv
        hsv = cv2.cvtColor(inimg, cv2.COLOR_BGR2HSV)
        
        arrayForHSV = list(self.stringForHSV)
        
        #jevois.sendSerial(str(len(arrayForHSV)))
        
        if len(arrayForHSV) == 16 and arrayForHSV[4] == "h":
            try:
                hLow = arrayForHSV[11] + arrayForHSV[12]
                #hHigh = arrayForHSV[16] + arrayForHSV[17] + arrayForHSV[18]
            except:
                hLow = "10"
                #hHigh = "10"
            try:
                hHigh = arrayForHSV[16] + arrayForHSV[17] + arrayForHSV[18]
            except:
                hHigh = "10"
            #jevois.sendSerial(low)
            #jevois.sendSerial(high)
            hLow = int(hLow)
            hHigh = int(hHigh)
            lowerThreshold = np.array([hLow, 50, 50])
            upperThreshold = np.array([hHigh, 255, 255])
            #jevois.sendSerial("helloooooooooo")
            jevois.sendSerial(str(lowerThreshold))
            #jevois.sendSerial("helllooooooo")
            jevois.sendSerial(str(upperThreshold))
        
        #jevois.sendSerial("alive")
        #jevois.sendSerial(str(arrayForHSV))
        #jevois.sendSerial(low)
        #jevois.sendSerial(high)
        
        oKernel = np.ones((2, 2), np.uint8)
        cKernel = np.ones((4, 4), np.uint8)
        #cKernel = np.array([
        #[1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1],
        #[1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1],
        #[0, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 0],
        #[0, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0],
        #[0, 0, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0]], dtype = np.uint8)
        
        
        #threshold colors to detect - Green: First value decides color, second val determines intensity, third val decides brightness
        lowerThreshold = np.array([45, 50, 50])
        upperThreshold = np.array([75, 255, 255])
        
        #check if color in range
        mask = cv2.inRange(hsv, lowerThreshold, upperThreshold)
        
        #create blur on image to reduce noise
        blur = cv2.GaussianBlur(mask,(5,5),0)
        
        ret,thresh = cv2.threshold(blur, 65, 255, cv2.THRESH_BINARY)
        
        #closes of noise from inside object
        closing = cv2.morphologyEx(thresh, cv2.MORPH_CLOSE, cKernel)
        
        #takes away noise from outside object
        opening = cv2.morphologyEx(closing, cv2.MORPH_OPEN, oKernel)
        
        #find contours
        contours, _ = cv2.findContours(opening, cv2.RETR_TREE, cv2.CHAIN_APPROX_NONE)
        
        cntArray = []
        
        for contour in contours:
            peri = cv2.arcLength(contour, True)
            approx = cv2.approxPolyDP(contour, 0.04 * peri, True)
            cntArea = cv2.contourArea(contour)
            if cntArea > 75 and cntArea < 300:
                cntArray.append(contour)
        
        sortedArray = self.sortContours(cntArray)
        
        if len(sortedArray) == 0:
            #jevois.sendSerial('{"Distance":-11, "Angle":-100}')
            #outimg = cv2.cvtColor(opening, cv2.COLOR_GRAY2BGR)
            outimg = cv2.cvtColor(hsv, cv2.COLOR_HSV2BGR)
            return outimg

        boxColor = (240,255, 255)
        
        target = cv2.minAreaRect(sortedArray[0])
        points_A = cv2.boxPoints(target)
        points_1 = np.int0(points_A)
        cv2.drawContours(hsv, [points_1], 0, boxColor, 2)
        targetX = target[0][0]
        targetY = target[0][1]
        
        
        yawAngle = (targetX - 159.5) * 0.203125
        #get actual thing later
        distance = -12
        
        yawAngle = str(yawAngle)
        distance = str(distance)
        
        JSON = '{"Distance":' + distance + ', "Angle":' + yawAngle + '}'
        
        #jevois.sendSerial(JSON)
        #jevois.sendSerial(yawAngle)
        
        #outimg = cv2.cvtColor(opening, cv2.COLOR_GRAY2BGR)
        outimg = cv2.cvtColor(hsv, cv2.COLOR_HSV2BGR)
        
        return outimg