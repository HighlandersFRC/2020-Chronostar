import libjevois as jevois
import cv2
import numpy as np
import json
import math
import time


class HoughTapeDetect:
# ###################################################################################################
## Constructor


    def __init__(self):
        # Instantiate a JeVois Timer to measure our processing framerate:
        self.timer = jevois.Timer("processing timer", 100, jevois.LOG_INFO)
        
        self.draw = True
        
        self.lowerH = 40
        self.upperH = 88
        
        self.lowerS = 224
        self.upperS = 255
        
        self.lowerV = 92
        self.upperV = 253
        
        #gain = 33
        #exposure = 33
        
        self.stringForHSV = "hi"
        self.stringforThresh = "hello"
        self.Thresh = 10
                
    # ###################################################################################################
    ## Process function with USB output
    def process(self, inframe, outframe):
        #out = self.UniversalProcess(inframe)
        #outframe.sendCv(out)
        #jevois.sendSerial("hello")
        out = self.UniversalProcess(inframe)
        #outframe.sendCv(out)

    def processNoUSB(self, inframe):
        out = self.UniversalProcess(inframe)
        #outframe.sendCv(out)
        #jevois.sendSerial("bonjour")
        
    def parseSerial(self, string):
        self.stringForHSV = string
        #jevois.sendSerial("hello parseSerial")
        #jevois.sendSerial(stringForHSV)
        return self.stringForHSV;
    #stringForHSV = string    


    def UniversalProcess(self, inframe):
        inimg = inframe.getCvBGR()
        outimg = inimg
        
        oKernel = (2,2)
    
        #change to hsv
        hsv = cv2.cvtColor(inimg, cv2.COLOR_BGR2HSV)
        
        arrayForHSV = list(self.stringForHSV)
        
        #threshold colors to detect - Green: First value decides color, second val determines intensity, third val decides brightness
        lowerThreshold = np.array([self.lowerH, self.lowerS, self.lowerV])
        upperThreshold = np.array([self.upperH, self.upperS, self.upperV])
        
        
        if len(arrayForHSV) > 15 and arrayForHSV[4] == "h":
            stringForH = self.stringForHSV.lstrip("set hrange")
            stringHSpace= stringForH.replace("..."," ")
            stringH = stringHSpace.split(" ")
            self.lowerH = int(stringH[0])
            self.upperH = int(stringH[1])
            lowerThreshold = np.array([self.lowerH, self.lowerS, self.lowerV])
            upperThreshold = np.array([self.upperH, self.upperS, self.upperV])
            #jevois.sendSerial("hello")
        if len(arrayForHSV) > 15 and arrayForHSV[4] == "s":
            stringForS = self.stringForHSV.lstrip("set srange")
            stringSSpace= stringForS.replace("..."," ")
            stringS = stringSSpace.split(" ")
            self.lowerS = int(stringS[0])
            self.upperS = int(stringS[1])
            lowerThreshold = np.array([self.lowerH, self.lowerS, self.lowerV])
            upperThreshold = np.array([self.upperH, self.upperS, self.upperV])
        if len(arrayForHSV) > 15 and arrayForHSV[4] == "v":
            stringForV = self.stringForHSV.lstrip("set vrange")
            stringVSpace= stringForV.replace("..."," ")
            stringV = stringVSpace.split(" ")
            self.lowerV = int(stringV[0])
            self.upperV = int(stringV[1])
            lowerThreshold = np.array([self.lowerH, self.lowerS, self.lowerV])
            upperThreshold = np.array([self.upperH, self.upperS, self.upperV])
        if len(arrayForHSV) > 15 and arrayForHSV[4] == "T":
            stringForThresh = self.stringForThresh.lstrip("setcam Thresh ")
            self.Thresh = int(stringForThresh[0])
        #jevois.sendSerial(str(lowerThreshold))
        #jevois.sendSerial(str(upperThreshold))

        #check if color in range
        maskimg = cv2.inRange(hsv, lowerThreshold, upperThreshold)
        
        result = cv2.bitwise_and(inimg, inimg, mask = maskimg)
        
        edges = cv2.Canny(maskimg, 150, 175)

        lines = 0

        distanceResInPixels = 1
        angleResInRadians = np.pi/180
        threshold = self.Thresh
        minLineLength = 30
        maxLineGap = 7
        minTheta = 0
        maxTheta = 180
        
        lines = cv2.HoughLinesP(edges, distanceResInPixels, angleResInRadians, threshold, lines, minTheta, maxTheta)
        
        #jevois.sendSerial(str(len(lines)))
        
        numLines = 0
        centerX = 0
        centerY = 0
        
        
        #REMEMBER TO CHANGE WHEN SIDEWAYS
        #REMEMBER TO CHANGE WHEN SIDEWAYS
        #REMEMBER TO CHANGE WHEN SIDEWAYS
        #REMEMBER TO CHANGE WHEN SIDEWAYS
        lowestHeight = 580
        
        farthestX = 0
        
        drawColor = (0, 0, 255)
        
        if lines is not None:
            for i in range(0, len(lines)):
                l = lines[i][0]
                drawColor = (255, 0, 0)
                #cv2.line(outimg, (l[0], l[1]), (l[2], l[3]), (0, 0, 0), 2, cv2.LINE_AA)
                #numLines = numLines + 1
                
                x1 = l[0]
                y1 = l[1]
                x2 = l[2]
                y2 = l[3]
                
                if l[1] < lowestHeight and l[3] < lowestHeight:
                    green = (0, 255, 0)
                    centerX = centerX + ((x2 + x1)/2)
                    if centerX > farthestX:
                        farthestX = centerX
                    
                    centerY = centerY + ((y2 + y1)/2)
                    #centerX = centerX + l[0] + l[2]
                    numLines = numLines + 1
                    cv2.line(outimg, (l[0], l[1]), (l[2], l[3]), green, 2, cv2.LINE_AA)
                    #jevois.sendSerial(str(l))
                    red = (255, 0, 0)
                    blue = (0, 0, 255)
                    cv2.line(result, (l[0], l[1]), (l[2], l[3]), red, 2, cv2.LINE_AA)
                        
        else:
            jevois.sendSerial('{"Distance":null, "Angle":null}')
            return outimg
            
        if numLines != 0:
            centerX = centerX/numLines
            centerY = centerY/numLines
            #centerY = centerY - 15 
            
        centerX = float(centerX)
        centerXforLines = int(centerX)
        centerY = float(centerY)
        centerYforLines = int(centerY)
        
        yawAngle = (centerY - 319.5) * 0.1015625
        yawAngle = str(yawAngle)
        distance = "-11"
        
        cv2.circle(outimg, (centerXforLines, centerYforLines), 5, (128, 128, 0))
        cv2.line(outimg, (centerXforLines, 0), (centerXforLines, 480), (128, 128, 0), 2, cv2.LINE_AA)
        cv2.line(outimg, (0, 0), (0, 240), (255, 0, 0), 2, cv2.LINE_AA)
        
        centerY = str(centerY)
        centerX = str(centerX)
        
        JSON = '{"Distance":' + centerX + ', "Angle":' + yawAngle + '}'
        
        #jevois.sendSerial("CenterX:" + str(centerX))
        #jevois.sendSerial("CenterY:" + str(centerY))
        jevois.sendSerial(JSON)
        #jevois.sendSerial(str(len(lines)))
        
        #time.sleep(3)
        
        #jevois.sendSerial("hello")
        
        return outimg
        #return outimg