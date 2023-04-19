import random
import time
import RPi.GPIO as GPIO

random.seed(int(time.time()))

PIN = 35

GPIO.setmode(GPIO.BOARD)
GPIO.setup(PIN, GPIO.IN, pull_up_down=GPIO.PUD_UP)

class Player:
    name = ""
    tmp_punkte = 0
    punkte = 0
    simulated = False

    def getTmpPunkte(self):
        return self.tmp_punkte;

    def reset(self):
        self.tmp_punkte = 0;

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

    def zug(self, auto = False):
        if(auto or getClicks() < 2):
            wurf = random.randint(1,6);
            self.tmp_punkte = self.tmp_punkte + wurf;
            return wurf;
        else:
            return False;

    def simulate(self):
        self.tmp_punkte = 18;
        self.simulated = True;

    def isSimulated(self):
        return self.simulated;


def getClicks():
    while GPIO.input(PIN) == 1:
        time.sleep(0.10)
    clicks = 1
    click_time = time.time();
    while time.time() < click_time + 0.7:
        time.sleep(0.15)
        if(GPIO.input(PIN) == 0):
            clicks = clicks + 1;
            click_time = time.time();
    return clicks

print ("======================")
print ("===   Black Jack   ===")
print ("======================")
print ("")
player = Player()
player.setName("Player")

computer = Player()
computer.setName("Computer")

runden = 0
while runden < 100000:
    skip = False;
    wurf = 0
    player.reset();
    player.simulate();
    computer.reset();
    if not player.isSimulated():
        print("");
        print("Runde beginnt")
        print("");

    while True and not player.isSimulated():
        wurf = player.zug()
        if wurf == False:
            break
        print("Du hast " + str(wurf) + " gewuerfelt!");
        print("Du hast nun " + str(player.getTmpPunkte()) + " Punkte!");
        print("");
        if (player.getTmpPunkte() == 21):
            print ("Somit hast du diese Runde gewonnen!");
            print ("Dein Punktestand: " + str(player.win()));
            skip = True;
            break;
        if (player.getTmpPunkte() > 21):
            print ("Somit hast du diese Runde verloren!");
            print("Punktestand vom Computer: " + str(computer.win()));
            skip = True;
            break;

    if not skip and not player.isSimulated():
        print("");
        print("Computer ist dran");
        print("");

    while(computer.getTmpPunkte() <= player.getTmpPunkte() and not skip):
        if ( not player.isSimulated()):
            time.sleep(2);
            wurf = computer.zug(True);
            tmp_punkte = computer.getTmpPunkte();
            print ("Der Computer hat " + str(wurf) + " gewuerfelt!");
            print ("Der Computer hat nun " + str(tmp_punkte) + " Punkte!");
            print ("");
            if(tmp_punkte > 21):
                print ("Somit hat der Computer verloren");
                print("Dein Punktestand: " + str(player.win()));
                break;
            if(tmp_punkte > player.getTmpPunkte()):
                print ("Somit hat der Computer gewonnen");
                print("Punktestand vom Computer: " + str(computer.win()));
        else:
            wurf = computer.zug(True);
            tmp_punkte = computer.getTmpPunkte();
            if(tmp_punkte > 21):
                player.win();
                break;
            if(tmp_punkte > player.getTmpPunkte()):
                computer.win();
                break;
    runden = runden + 1;

    skip = False;


if(computer.getPoints() > player.getPoints()):
    print ("");
    print ("Der Computer hat mit " + str(computer.getPoints()) + " Runden gewonnen!");
elif(computer.getPoints() < player.getPoints()):
    print ("");
    print ("Du hast mit " + str(player.getPoints()) + " Runden gewonnen!");
else:
    print("")
    print ("Unentschieden, jeder hat " + str(player.getPoints()) + " Runden gewonnen!");
