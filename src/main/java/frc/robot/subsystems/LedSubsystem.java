package frc.robot.subsystems;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.MuratCont;

public class LedSubsystem extends SubsystemBase{
    private AddressableLED led;
    private AddressableLEDBuffer ledBuffer;
    private boolean ifBusy = false;
    public static int LedCount = 138;

    public LedSubsystem(){

        //PWM pini (header)
        led = new AddressableLED(0);

        //Lenght
        ledBuffer = new AddressableLEDBuffer(LedCount); //Eski koddan aldım lenght'i
        led.setLength(ledBuffer.getLength());

        led.setData(ledBuffer);
        led.start();
    }

    public void LinearBlue(){
        for(int i = 0;i<ledBuffer.getLength();i++){
            ledBuffer.setRGB(i, 0, 0, 255);
        }

        led.setData(ledBuffer);
    }
    public void LinearRed(){
        for(int i = 0;i<ledBuffer.getLength();i++){
            ledBuffer.setRGB(i, 255, 0, 0);
        }

        led.setData(ledBuffer);
    }
    public void LinearGreen(){
        for(int i = 0;i<ledBuffer.getLength();i++){
            ledBuffer.setRGB(i, 0, 255, 0);
        }

        led.setData(ledBuffer);
    }

    public void TestGreen(){
        int AddLedCount = 30;
        for(int i=LedCount/2;i>=LedCount/2+AddLedCount;i++){
            ledBuffer.setRGB(i, 0, 255, 0);
        }
        for(int i=LedCount/2;i>=LedCount/2-AddLedCount;i--){
            ledBuffer.setRGB(i, 0, 255, 0);
        }
        
        led.setData(ledBuffer);
    }



    public Command LedCommand(Indexer s_Indexer){
        return run(()->{
            if(ifBusy == false){
                if(MuratCont.yesilbas == true){
                    TestGreen();
                }
                else {
                    LinearBlue();
                }
            }
        });
    }
}
