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
public class Note
{
    String NoteCd;
    double Frequency;
    double Wavelength;
    int SetRange;
    int pwm;
    int SetClock;

    public Note(String NoteCd, double Frequency, double Wavelength, int SetRange, int pwm, int SetClock)
    {
        this.NoteCd = NoteCd;
        this.Frequency = Frequency;
        this.Wavelength = Wavelength;
        this.SetRange = SetRange;
        this.pwm = pwm;
        this.SetClock = SetClock;
    }
}
