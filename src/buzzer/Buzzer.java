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
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import static com.pi4j.io.gpio.PinState.HIGH;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;
import static com.pi4j.wiringpi.Gpio.OUTPUT;
import static com.pi4j.wiringpi.Gpio.digitalWrite;
import static com.pi4j.wiringpi.Gpio.pinMode;
import static com.pi4j.wiringpi.Gpio.pinModeAlt;
import com.pi4j.wiringpi.SoftTone;
import java.time.LocalTime;

public class Buzzer
{
    private static void TestGpioPins() throws InterruptedException
    {
        Gpio.wiringPiSetup();
        String pinName = "";
        Pin[] pins = RaspiPin.allPins();
        for (Pin pin : pins)
        {
            try
            {
                pinName = pin.getName();
                int pinAddress = pin.getAddress();
                pinModeAlt(pinAddress, OUTPUT);
                digitalWrite(pinAddress, true);
                System.out.println("OK-" + pinName);
            } catch (Exception e)
            {
                System.out.println("KO-" + pinName);
            }
        }
        
        ///*KO*/ledPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "GPIO_07_On_GPIO04", PinState.LOW);
        LocalTime time = LocalTime.now();
        LocalTime endTime = time.plusSeconds(2);
        boolean keepRunning = true;
        while (keepRunning)
        {
            time = LocalTime.now();
            if (time.isAfter(endTime))
            {
                keepRunning = false;
            }
        }
        for (Pin pin : pins)
        {
            try
            {
                pinName = pin.getName();
                int pinAddress = pin.getAddress();
                pinModeAlt(pinAddress, OUTPUT);
                digitalWrite(pinAddress, false);
            } catch (Exception e)
            {
            }
        }
    }

    private static void TestGpioPinsRaw(GpioController gpio) throws InterruptedException
    {
        GpioPinDigitalOutput ledPin;
        String pinName = "";
        Pin[] pins = RaspiPin.allPins();
        for (Pin pin : pins)
        {
            try
            {
                pinName = pin.getName();
                ledPin = gpio.provisionDigitalOutputPin(pin, pin.getName(), PinState.LOW);
                ledPin.setShutdownOptions(true, PinState.LOW);
                ledPin.high();
                System.out.println("OK-" + pinName);
            } catch (Exception e)
            {
                System.out.println("KO-" + pinName);
            }
        }
        
        ///*KO*/ledPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "GPIO_07_On_GPIO04", PinState.LOW);
        LocalTime time = LocalTime.now();
        LocalTime endTime = time.plusSeconds(2);
        boolean keepRunning = true;
        while (keepRunning)
        {
            time = LocalTime.now();
            if (time.isAfter(endTime))
            {
                keepRunning = false;
            }
        }
        gpio.shutdown();
    }
    
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
        int pin = 7;
        //frequency between 0 (off) and 5000Hz
        int frequency = 330;
        SoftTone.softToneWrite(pin, frequency);
        Thread.sleep(1000);
        SoftTone.softToneWrite(pin, 0);
    }

    public static void PassiveBuzzerPwm(GpioController gpio, String[] args) throws InterruptedException
    {
        /*final GpioPinPwmOutput buzzerPin = gpio.provisionPwmOutputPin(RaspiPin.GPIO_01);
        //buzzerPin.setShutdownOptions(true, PinState.LOW);
        //PwmExample
        buzzerPin.setMode(PinMode.PWM_OUTPUT);
        buzzerPin.setPwm(50);
        buzzerPin.setPwmRange(1024);*/

        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate code)
        ////final Console console = new Console();
        // print program title/header
        ////console.title("<-- The Pi4J Project -->", "PWM Example");
        // allow for user to exit program using CTRL-C
        ////console.promptForExit();
        // create GPIO controller instance
        //GpioController gpio = GpioFactory.getInstance();
        // All Raspberry Pi models support a hardware PWM pin on GPIO_01.
        // Raspberry Pi models A+, B+, 2B, 3B also support hardware PWM pins: GPIO_23, GPIO_24, GPIO_26
        //
        // by default we will use gpio pin #01; however, if an argument
        // has been provided, then lookup the pin by address
//        Pin pin = CommandArgumentParser.getPin(
//                RaspiPin.class, // pin provider class to obtain pin instance from
//                RaspiPin.GPIO_01, // default pin if no pin argument found
//                args);             // argument array to search in
        Pin pin = RaspiPin.GPIO_23;

        GpioPinPwmOutput pwm = gpio.provisionPwmOutputPin(pin);

        // you can optionally use these wiringPi methods to further customize the PWM generator
        // see: http://wiringpi.com/reference/raspberry-pi-specifics/
        com.pi4j.wiringpi.Gpio.pwmSetMode(com.pi4j.wiringpi.Gpio.PWM_MODE_MS);
        com.pi4j.wiringpi.Gpio.pwmSetRange(1000);
        com.pi4j.wiringpi.Gpio.pwmSetClock(500);

        // set the PWM rate to 500
        pwm.setPwm(500);

        // set the PWM rate to 250
        pwm.setPwm(250);

        // set the PWM rate to 0
        pwm.setPwm(0);

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        gpio.shutdown();
    }

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException
    {
        //ActiveBuzzer(GpioFactory.getInstance());
        //PassiveBuzzer();
        //PassiveBuzzerPwm(GpioFactory.getInstance(), args);
        TestGpioPinsRaw(GpioFactory.getInstance());
        //TestGpioPins();
    }
}
