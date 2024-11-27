package frc.robot.subsystems;
import org.ejml.equation.Function;
import org.photonvision.PhotonCamera;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.MuratCont;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import java.util.Arrays;
import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class LedSubsystem extends SubsystemBase{



    private AddressableLED led;
    private int m_rainbowFirstPixelHue;
    private int GreenBlinkChecker = 0;
    private AddressableLEDBuffer ledBuffer;
    public static int LedCount = 138;

    public LedSubsystem(SwerveSubsystem s_swerve){


        //PWM pini (header)
        led = new AddressableLED(0);

        //Lenght
        ledBuffer = new AddressableLEDBuffer(LedCount); //Eski koddan aldım lenght'i
        led.setLength(ledBuffer.getLength());

        led.setData(ledBuffer);
        led.start();

        m_rainbowFirstPixelHue = 0;

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

    public void rainbow() {
        // For every pixel
        for (var i = 0; i < ledBuffer.getLength(); i++) {
          // Calculate the hue - hue is easier for rainbows because the color
          // shape is a circle so only one value needs to precess
          final var hue = (m_rainbowFirstPixelHue + (i * 180 / ledBuffer.getLength())) % 180;
          // Set the value
          ledBuffer.setHSV(i, hue, 255, 128);
        }
        // Increase by to make the rainbow "move"
        m_rainbowFirstPixelHue += 3;
        // Check bounds
        m_rainbowFirstPixelHue %= 180;
      }

    public void BlinkGreen(){
        if(GreenBlinkChecker % 2 == 0){
            for(int i = 0;i<ledBuffer.getLength();i++){
                ledBuffer.setRGB(i, 0, 255, 0);
            }
        }
        else{
            for(int i = 0;i<ledBuffer.getLength();i++){
                ledBuffer.setRGB(i, 0, 100, 0);
            }
        }
        led.setData(ledBuffer);
        GreenBlinkChecker += 1;
    }

    public void ColorByAlliance(){
        Optional<Alliance> ally = DriverStation.getAlliance();
        if(ally.isPresent()){
            if(ally.get()==Alliance.Red){
                LinearRed();
            }
            if(ally.get()==Alliance.Blue){
                LinearBlue();
            }
        }
        else{
            rainbow();
        }
    }

    public void RankChooser(int rank){
        switch (rank) {
            case 0:
                ColorByAlliance();
                break;
            case 1:
                ColorByAlliance();
            case 2:
                BlinkGreen();
            default:
                break;
        }
    }



    public Command LedCommand(boolean[] FR){
        return run(()->{
            for(int i=0;i<FR.length;i++){
                if(FR[i]==true){
                    RankChooser(i);
                    break;
                }
            }
        });
    }
}
