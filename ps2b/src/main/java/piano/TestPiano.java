package piano;

import javax.sound.midi.MidiUnavailableException;

import midi.Midi;
import music.Pitch;
import java.lang.*;
/**
 * Created by ngaiman_cheung on 17/10/16.
 */
public class TestPiano {
    public static void main(String[] args) {

        try {
            Midi midi;
            PianoMachine pm;
            midi = Midi.getInstance();
            midi.clearHistory();
            pm = new PianoMachine();

            pm.beginNote(new Pitch(0));
            //System.out.println("C");
            Midi.rest(50);
            pm.endNote(new Pitch(0));

            pm.changeInstrument();
            //System.out.println("changeInstr");
            Midi.rest(10);
            pm.changeInstrument();

            pm.beginNote(new Pitch(2));
            //System.out.println("D");
            Midi.rest(50);
            pm.endNote(new Pitch(2));

            System.out.println(midi.history());
            midi.clearHistory();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }



    }

}