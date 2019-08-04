/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buzzer;

/**
 *
 * @author marius
 */
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftTone;
import java.time.LocalTime;

public class Buzzer
{

    public static void ActiveBuzzer(GpioController gpio) throws InterruptedException
    {
        final GpioPinDigitalOutput buzzerPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "GPIO_00_On_GPIO17", PinState.LOW);
        buzzerPin.setShutdownOptions(true, PinState.LOW);
        LocalTime time = LocalTime.now();
        LocalTime endTime = time.plusSeconds(2);
        boolean keepRunning = true;
        while (keepRunning)
        {
            buzzerPin.high();
            Thread.sleep(100);
            buzzerPin.low();
            Thread.sleep(100);

            time = LocalTime.now();
            if (time.isAfter(endTime))
            {
                keepRunning = false;
            }
        }
    }

    public static void PassiveBuzzer() throws InterruptedException
    {
        Gpio.wiringPiSetup();
        SoftTone.softToneCreate(0);
        //uses the WiringPi pin (http://wiringpi.com/pins/)
        //0 == RaspiPin.GPIO_00 == BCM GPIO17
        int pin = 0;
        //frequency between 0 (off) and 5000Hz
        int frequency = 330;
        SoftTone.softToneWrite(pin, frequency);
        Thread.sleep(1000);
        SoftTone.softToneWrite(pin, 0);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException
    {
        //ActiveBuzzer(GpioFactory.getInstance());
        PassiveBuzzer();
    }
}
