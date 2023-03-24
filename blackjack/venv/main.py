import random
import time
import RPi.GPIO

PIN = 35

class Player:
    name = ""
    tmp_punkte = 0
    punkte = 0

    def getTmpPunkte(self):
        return self.tmp_punkte;

    def win(self):
        self.punkte = self.punkte + 1;
        return self.punkte;

    def getPoints(self):
        return self.punkte;

    def setName(self, name=""):
        if(name==""):
            self.name = input("Wähle deinen Namen: ")
        else:
            self.name = name

    def getName(self):
        return self.name

    def zug(self):
        if(getClicks < 2):
            # user rolled the dice
            self.tmp_punkte = self.tmp_punkte + random.randomint(1,6);
        else:
            # user double clicked -> skip
            return false;


def getClicks():
    while not GPIO.input(PIN)
        # wait for first click
        time.sleep(0.10)
    clicks = 1
    click_time = time.time();
    while time.time() < click_time + 0.5:
        # check for another click
        time.sleep(0.15)
        if(GPIO.input(PIN)):
            # detected another click
            clicks = clicks + 1
            # restart 50ms timer to detect more clicks
            click_time = time.time();
    return clicks

print ("======================")
print ("===   Black Jack   ===")
print ("======================")
print ("")
player = Player()
player.setName()

computer = Player()
computer.setName("Computer")

runden = 0
while runden < 3:
    skip = false;
    wurf = 0

    print("Runde beginnt")

    while True:
        wurf = player.zug()
        if not wurf:
            # player skipped ( 2 click )
            break
        print("Du hast " + wurf + " gewuerfelt!");
        print("Du hast nun " + player.getTmpPunkte() + " Punkte!");
        if (player.getTmpPunkte() == 21):
            print ("Somit hast du diese Runde gewonnen!");
            print ("Dein Punktestand: "+player.win());
            skip = true;
            break;
        if (player.getTmpPunkte() > 21):
            print ("Somit hast du diese Runde verloren!");
            skip = true;
            break;

    print("Computer ist dran");

    while(computer.getTmpPunkte() <= player.getTmpPunkte() and not skip):
        wurf = computer.zug();
        tmp_punkte = computer.getTmpPunkte();
        print ("Der Computer hat " + wurf + " gewuerfelt!");
        print ("Der Computer hat nun " + tmp_punkte + " Punkte!");
        if(tmp_punkte > 21):
            print ("Somit hat der Computer verloren");
            print("Dein Punktestand: " + player.win());
            break;
        if(tmp_punkte > player.getTmpPunkte()):
            print ("Somit hat der Computer gewonnen");
            print("Punktestand vom Computer: "+ computer.win());
    runden = runde + 1;

if(computer.getPoints() > player.getPoints()):
    print ("Der Computer hat mit " + computer.getPoints() + " gewonnen!");
else:
    print ("Du hast mit " + player.getPoints() + " gewonnen!");
