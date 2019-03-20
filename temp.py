import speech_recognition as sr
import pyttsx3
from sys import exit
r = sr.Recognizer()        #imports
with sr.Microphone() as source: 
    engine = pyttsx3.init()
    voices = engine.getProperty('voices')
    engine.setProperty('voice', voices[1].id) #voices of 0 da ragel
    engine.setProperty('rate', 120) #to change the speed of the voice
    
    
    while True:
                           #empty TXT file
        engine.say("how can i help you sir")
        engine.runAndWait()
        engine.stop() 
        print("Speak Anything :")
        audio = r.listen(source)
        
        try:
            text = r.recognize_google(audio)
            print("You said : {}".format(text))
        except:
            print("Sorry could not recognize what you said")
            continue
        
        try:
            text_file = open("Output.TXT", "w")
            text_file.write(text)
            text_file.close()
            print("I wrote")
        except:
            print("could not write")
            
        while True:
            f = open('Output.TXT', 'r')
            x = f.readline()
            print(x)
            f.close()
            if x!="":
                if x[0] == '$' :
                    break
                if x[0]== '!':
                     engine.say(x[1:len(x)])
                     engine.runAndWait()
                     engine.stop()
                     exit()
        engine.say(x[1:len(x)])
        engine.runAndWait()
        engine.stop()
        try:
           text_file = open("Output.TXT", "w")
           text_file.write(text)
           text_file.close()
        except:
            print("could not writes")
            
        
