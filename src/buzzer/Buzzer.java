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
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;
import static com.pi4j.wiringpi.Gpio.OUTPUT;
import static com.pi4j.wiringpi.Gpio.digitalWrite;
import static com.pi4j.wiringpi.Gpio.pinModeAlt;
import com.pi4j.wiringpi.SoftTone;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.list;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

    private static List<Note> BuildNotes()
    {
        List<Note> Notes = new ArrayList<Note>();
        Notes.add(new Note("C0", 16.35, 2109.89, 4697, 2349, 4008));
        Notes.add(new Note("Cs0", 17.32, 1991.47, 4434, 2217, 3783));
        Notes.add(new Note("Db0", 17.32, 1991.47, 4434, 2217, 3783));
        Notes.add(new Note("D0", 18.35, 1879.69, 4185, 2093, 3571));
        Notes.add(new Note("Ds0", 19.45, 1774.2, 3949, 1975, 3369));
        Notes.add(new Note("Eb0", 19.45, 1774.2, 3949, 1975, 3369));
        Notes.add(new Note("E0", 20.6, 1674.62, 3728, 1864, 3181));
        Notes.add(new Note("F0", 21.83, 1580.63, 3518, 1759, 3002));
        Notes.add(new Note("Fs0", 23.12, 1491.91, 3322, 1661, 2834));
        Notes.add(new Note("Gb0", 23.12, 1491.91, 3322, 1661, 2834));
        Notes.add(new Note("G0", 24.5, 1408.18, 3135, 1568, 2675));
        Notes.add(new Note("Gs0", 25.96, 1329.14, 2958, 1479, 2524));
        Notes.add(new Note("Ab0", 25.96, 1329.14, 2958, 1479, 2524));
        Notes.add(new Note("A0", 27.5, 1254.55, 2793, 1397, 2383));
        Notes.add(new Note("As0", 29.14, 1184.13, 2636, 1318, 2249));
        Notes.add(new Note("Bb0", 29.14, 1184.13, 2636, 1318, 2249));
        Notes.add(new Note("B0", 30.87, 1117.67, 2488, 1244, 2123));
        Notes.add(new Note("C1", 32.7, 1054.94, 2349, 1175, 2004));
        Notes.add(new Note("Cs1", 34.65, 995.73, 2216, 1108, 1891));
        Notes.add(new Note("Db1", 34.65, 995.73, 2216, 1108, 1891));
        Notes.add(new Note("D1", 36.71, 939.85, 2092, 1046, 1785));
        Notes.add(new Note("Ds1", 38.89, 887.1, 1975, 988, 1685));
        Notes.add(new Note("Eb1", 38.89, 887.1, 1975, 988, 1685));
        Notes.add(new Note("E1", 41.2, 837.31, 1864, 932, 1591));
        Notes.add(new Note("F1", 43.65, 790.31, 1759, 880, 1501));
        Notes.add(new Note("Fs1", 46.25, 745.96, 1661, 831, 1417));
        Notes.add(new Note("Gb1", 46.25, 745.96, 1661, 831, 1417));
        Notes.add(new Note("G1", 49, 704.09, 1567, 784, 1337));
        Notes.add(new Note("Gs1", 51.91, 664.57, 1479, 740, 1262));
        Notes.add(new Note("Ab1", 51.91, 664.57, 1479, 740, 1262));
        Notes.add(new Note("A1", 55, 627.27, 1396, 698, 1191));
        Notes.add(new Note("As1", 58.27, 592.07, 1318, 659, 1125));
        Notes.add(new Note("Bb1", 58.27, 592.07, 1318, 659, 1125));
        Notes.add(new Note("B1", 61.74, 558.84, 1244, 622, 1061));
        Notes.add(new Note("C2", 65.41, 527.47, 1174, 587, 1002));
        Notes.add(new Note("Cs2", 69.3, 497.87, 1108, 554, 946));
        Notes.add(new Note("Db2", 69.3, 497.87, 1108, 554, 946));
        Notes.add(new Note("D2", 73.42, 469.92, 1046, 523, 893));
        Notes.add(new Note("Ds2", 77.78, 443.55, 987, 494, 842));
        Notes.add(new Note("Eb2", 77.78, 443.55, 987, 494, 842));
        Notes.add(new Note("E2", 82.41, 418.65, 932, 466, 795));
        Notes.add(new Note("F2", 87.31, 395.16, 880, 440, 751));
        Notes.add(new Note("Fs2", 92.5, 372.98, 830, 415, 708));
        Notes.add(new Note("Gb2", 92.5, 372.98, 830, 415, 708));
        Notes.add(new Note("G2", 98, 352.04, 784, 392, 669));
        Notes.add(new Note("Gs2", 103.83, 332.29, 740, 370, 631));
        Notes.add(new Note("Ab2", 103.83, 332.29, 740, 370, 631));
        Notes.add(new Note("A2", 110, 313.64, 698, 349, 596));
        Notes.add(new Note("As2", 116.54, 296.03, 659, 330, 562));
        Notes.add(new Note("Bb2", 116.54, 296.03, 659, 330, 562));
        Notes.add(new Note("B2", 123.47, 279.42, 622, 311, 531));
        Notes.add(new Note("C3", 130.81, 263.74, 587, 294, 501));
        Notes.add(new Note("Cs3", 138.59, 248.93, 554, 277, 473));
        Notes.add(new Note("Db3", 138.59, 248.93, 554, 277, 473));
        Notes.add(new Note("D3", 146.83, 234.96, 523, 262, 446));
        Notes.add(new Note("Ds3", 155.56, 221.77, 494, 247, 421));
        Notes.add(new Note("Eb3", 155.56, 221.77, 494, 247, 421));
        Notes.add(new Note("E3", 164.81, 209.33, 466, 233, 398));
        Notes.add(new Note("F3", 174.61, 197.58, 440, 220, 375));
        Notes.add(new Note("Fs3", 185, 186.49, 415, 208, 354));
        Notes.add(new Note("Gb3", 185, 186.49, 415, 208, 354));
        Notes.add(new Note("G3", 196, 176.02, 392, 196, 334));
        Notes.add(new Note("Gs3", 207.65, 166.14, 370, 185, 316));
        Notes.add(new Note("Ab3", 207.65, 166.14, 370, 185, 316));
        Notes.add(new Note("A3", 220, 156.82, 349, 175, 298));
        Notes.add(new Note("As3", 233.08, 148.02, 330, 165, 281));
        Notes.add(new Note("Bb3", 233.08, 148.02, 330, 165, 281));
        Notes.add(new Note("B3", 246.94, 139.71, 311, 156, 265));
        Notes.add(new Note("C4", 261.63, 131.87, 294, 147, 250));
        Notes.add(new Note("Cs4", 277.18, 124.47, 277, 139, 236));
        Notes.add(new Note("Db4", 277.18, 124.47, 277, 139, 236));
        Notes.add(new Note("D4", 293.66, 117.48, 262, 131, 223));
        Notes.add(new Note("Ds4", 311.13, 110.89, 247, 124, 211));
        Notes.add(new Note("Eb4", 311.13, 110.89, 247, 124, 211));
        Notes.add(new Note("E4", 329.63, 104.66, 233, 117, 199));
        Notes.add(new Note("F4", 349.23, 98.79, 220, 110, 188));
        Notes.add(new Note("Fs4", 369.99, 93.24, 208, 104, 177));
        Notes.add(new Note("Gb4", 369.99, 93.24, 208, 104, 177));
        Notes.add(new Note("G4", 392, 88.01, 196, 98, 167));
        Notes.add(new Note("Gs4", 415.3, 83.07, 185, 93, 158));
        Notes.add(new Note("Ab4", 415.3, 83.07, 185, 93, 158));
        Notes.add(new Note("A4", 440, 78.41, 175, 88, 149));
        Notes.add(new Note("As4", 466.16, 74.01, 165, 83, 141));
        Notes.add(new Note("Bb4", 466.16, 74.01, 165, 83, 141));
        Notes.add(new Note("B4", 493.88, 69.85, 156, 78, 133));
        Notes.add(new Note("C5", 523.25, 65.93, 147, 74, 125));
        Notes.add(new Note("Cs5", 554.37, 62.23, 139, 70, 118));
        Notes.add(new Note("Db5", 554.37, 62.23, 139, 70, 118));
        Notes.add(new Note("D5", 587.33, 58.74, 131, 66, 112));
        Notes.add(new Note("Ds5", 622.25, 55.44, 123, 62, 105));
        Notes.add(new Note("Eb5", 622.25, 55.44, 123, 62, 105));
        Notes.add(new Note("E5", 659.25, 52.33, 116, 58, 99));
        Notes.add(new Note("F5", 698.46, 49.39, 110, 55, 94));
        Notes.add(new Note("Fs5", 739.99, 46.62, 104, 52, 89));
        Notes.add(new Note("Gb5", 739.99, 46.62, 104, 52, 89));
        Notes.add(new Note("G5", 783.99, 44.01, 98, 49, 84));
        Notes.add(new Note("Gs5", 830.61, 41.54, 92, 46, 79));
        Notes.add(new Note("Ab5", 830.61, 41.54, 92, 46, 79));
        Notes.add(new Note("A5", 880, 39.2, 87, 44, 74));
        Notes.add(new Note("As5", 932.33, 37, 82, 41, 70));
        Notes.add(new Note("Bb5", 932.33, 37, 82, 41, 70));
        Notes.add(new Note("B5", 987.77, 34.93, 78, 39, 66));
        Notes.add(new Note("C6", 1046.5, 32.97, 73, 37, 63));
        Notes.add(new Note("Cs6", 1108.73, 31.12, 69, 35, 59));
        Notes.add(new Note("Db6", 1108.73, 31.12, 69, 35, 59));
        Notes.add(new Note("D6", 1174.66, 29.37, 65, 33, 56));
        Notes.add(new Note("Ds6", 1244.51, 27.72, 62, 31, 53));
        Notes.add(new Note("Eb6", 1244.51, 27.72, 62, 31, 53));
        Notes.add(new Note("E6", 1318.51, 26.17, 58, 29, 50));
        Notes.add(new Note("F6", 1396.91, 24.7, 55, 28, 47));
        Notes.add(new Note("Fs6", 1479.98, 23.31, 52, 26, 44));
        Notes.add(new Note("Gb6", 1479.98, 23.31, 52, 26, 44));
        Notes.add(new Note("G6", 1567.98, 22, 49, 25, 42));
        Notes.add(new Note("Gs6", 1661.22, 20.77, 46, 23, 39));
        Notes.add(new Note("Ab6", 1661.22, 20.77, 46, 23, 39));
        Notes.add(new Note("A6", 1760, 19.6, 44, 22, 37));
        Notes.add(new Note("As6", 1864.66, 18.5, 41, 21, 35));
        Notes.add(new Note("Bb6", 1864.66, 18.5, 41, 21, 35));
        Notes.add(new Note("B6", 1975.53, 17.46, 39, 20, 33));
        Notes.add(new Note("C7", 2093, 16.48, 37, 19, 31));
        Notes.add(new Note("Cs7", 2217.46, 15.56, 35, 18, 30));
        Notes.add(new Note("Db7", 2217.46, 15.56, 35, 18, 30));
        Notes.add(new Note("D7", 2349.32, 14.69, 33, 17, 28));
        Notes.add(new Note("Ds7", 2489.02, 13.86, 31, 16, 26));
        Notes.add(new Note("Eb7", 2489.02, 13.86, 31, 16, 26));
        Notes.add(new Note("E7", 2637.02, 13.08, 29, 15, 25));
        Notes.add(new Note("F7", 2793.83, 12.35, 27, 14, 23));
        Notes.add(new Note("Fs7", 2959.96, 11.66, 26, 13, 22));
        Notes.add(new Note("Gb7", 2959.96, 11.66, 26, 13, 22));
        Notes.add(new Note("G7", 3135.96, 11, 24, 12, 21));
        Notes.add(new Note("Gs7", 3322.44, 10.38, 23, 12, 20));
        Notes.add(new Note("Ab7", 3322.44, 10.38, 23, 12, 20));
        Notes.add(new Note("A7", 3520, 9.8, 22, 11, 19));
        Notes.add(new Note("As7", 3729.31, 9.25, 21, 11, 18));
        Notes.add(new Note("Bb7", 3729.31, 9.25, 21, 11, 18));
        Notes.add(new Note("B7", 3951.07, 8.73, 19, 10, 17));
        Notes.add(new Note("C8", 4186.01, 8.24, 18, 9, 16));
        Notes.add(new Note("Cs8", 4434.92, 7.78, 17, 9, 15));
        Notes.add(new Note("Db8", 4434.92, 7.78, 17, 9, 15));
        Notes.add(new Note("D8", 4698.63, 7.34, 16, 8, 14));
        Notes.add(new Note("Ds8", 4978.03, 6.93, 15, 8, 13));
        Notes.add(new Note("Eb8", 4978.03, 6.93, 15, 8, 13));
        Notes.add(new Note("E8", 5274.04, 6.54, 15, 8, 12));
        Notes.add(new Note("F8", 5587.65, 6.17, 14, 7, 12));
        Notes.add(new Note("Fs8", 5919.91, 5.83, 13, 7, 11));
        Notes.add(new Note("Gb8", 5919.91, 5.83, 13, 7, 11));
        Notes.add(new Note("G8", 6271.93, 5.5, 12, 6, 10));
        Notes.add(new Note("Gs8", 6644.88, 5.19, 12, 6, 10));
        Notes.add(new Note("Ab8", 6644.88, 5.19, 12, 6, 10));
        Notes.add(new Note("A8", 7040, 4.9, 11, 6, 9));
        Notes.add(new Note("As8", 7458.62, 4.63, 10, 5, 9));
        Notes.add(new Note("Bb8", 7458.62, 4.63, 10, 5, 9));
        Notes.add(new Note("B8", 7902.13, 4.37, 10, 5, 8));

        return Notes;
    }

    private static void SetFrequencyByClock(int Clock, int duration) throws InterruptedException
    {
        com.pi4j.wiringpi.Gpio.pwmSetClock(Clock);//2 to 4095
        Thread.sleep(duration);
    }

    private static List<Song> BuildSong(List<Note> Notes)
    {
        List<Song> songs = new ArrayList<Song>();

        Note result = Notes.stream().filter(item -> item.NoteCd.equals("C4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("C4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("C5")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("A3")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("A4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("As3")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("As4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("C4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("C5")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("A3")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("A4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("As3")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("As4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("F3")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("F4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("D3")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("D4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("Ds3")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("Ds4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("F3")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("F4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("D3")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("D4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("Ds3")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("Ds4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("Ds4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("Cs4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("D4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("Cs4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("Ds4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("Ds4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("Gs3")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("G3")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("Cs4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("C4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("Fs4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("F4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("E3")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("As4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("A4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("Gs4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("Ds4")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("B3")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("As3")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("A3")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        result = Notes.stream().filter(item -> item.NoteCd.equals("Gs3")).collect(Collectors.toList()).get(0);
        songs.add(new Song(result, 200));
        
        return songs;
    }

    public static void PassiveBuzzerPwm(GpioController gpio, String[] args) throws InterruptedException
    {
        LocalTime time = LocalTime.now();
        LocalTime endTime = time.plusSeconds(2);
        boolean keepRunning = true;

        // ref: http://wiringpi.com/reference/raspberry-pi-specifics/
        com.pi4j.wiringpi.Gpio.pwmSetMode(com.pi4j.wiringpi.Gpio.PWM_MODE_MS);

        com.pi4j.wiringpi.Gpio.pwmSetRange(293); //? to 4096
        Pin pin = RaspiPin.GPIO_23;
        GpioPinPwmOutput pwm = gpio.provisionPwmOutputPin(pin, "pwm1", OUTPUT);
        pwm.setPwm(147);

        List<Note> Notes = BuildNotes();
        List<Song> Song1 = BuildSong(Notes);

        Song1.forEach(k ->
        {
            try
            {
                SetFrequencyByClock(k.note.SetClock, k.tone);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(Buzzer.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

//        Notes.forEach(k ->
//        {
//            try
//            {
//                SetFrequencyByClock(k.SetClock, 50);
//                System.out.println(k.NoteCd);
//            } catch (InterruptedException ex)
//            {
//                Logger.getLogger(Buzzer.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });
//
//        Collections.reverse(Notes);
//
//        Notes.forEach(k ->
//        {
//            try
//            {
//                SetFrequencyByClock(k.SetClock, 50);
//            } catch (InterruptedException ex)
//            {
//                Logger.getLogger(Buzzer.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });
//
//        while (keepRunning)
//        {
//            time = LocalTime.now();
//            if (time.isAfter(endTime))
//            {
//                keepRunning = false;
//            }
//        }
        pwm.setPwm(0);

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
        PassiveBuzzerPwm(GpioFactory.getInstance(), args);
        //TestGpioPinsRaw(GpioFactory.getInstance());
        //TestGpioPins();
    }
}
