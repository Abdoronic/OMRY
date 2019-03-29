import speech_recognition as sr
import pyttsx3
from sys import exit
r = sr.Recognizer()        #imports
with sr.Microphone() as source: 
    engine = pyttsx3.init()
    voices = engine.getProperty('voices')
    engine.setProperty('voice', voices[1].id) #voices of 0 da ragel
    engine.setProperty('rate', 120) #to change the speed of the voice
    path = "D:\Eclipse\OMRY\src\disk\output.txt";
    
                           #empty TXT file
    engine.say("how can i help you sir")
    engine.runAndWait()
    engine.stop() 
    print("Speak Anything :")
    audio = r.listen(source)
    
    try:
        text = r.recognize_google(audio)
        print(format(text))
    except:
        print("Sorry could not recognize what you said")
    
    try:
        text_file = open(path, "w")
        text_file.write(text)
        text_file.close()
    except:
        print("could not write")
        
  
