package piano;

import javax.sound.midi.MidiUnavailableException;

import midi.*;
import music.*;

import java.util.ArrayList;
//import java.util.Date;
import static midi.Midi.DEFAULT_INSTRUMENT;

public class PianoMachine {
	
	private Midi midi;
    //private Pitch isPlaying = null;
    private ArrayList<Pitch> isPlaying = new ArrayList<>();
    private Instrument instrument = DEFAULT_INSTRUMENT;
    private int octave = 0;
    private boolean isRecording = false;
    private ArrayList<NoteEvent> recording = new ArrayList<>();
    //private long timestamp;

	/**
	 * constructor for PianoMachine.
	 * 
	 * initialize midi device and any other state that we're storing.
	 */
    public PianoMachine() {
    	try {
            midi = Midi.getInstance();
        } catch (MidiUnavailableException e1) {
            System.err.println("Could not initialize midi device");
            e1.printStackTrace();
            return;
        }
    }

    /**
     * Start a note.
     * When one of these keys is pressed, a note should begin if it isn't already sounding.
     *
     * @param rawPitch: pitch with value corresponding to 12 pitches in an octave, 0 --> 'C'
     */
    public void beginNote(Pitch rawPitch) {
        Pitch pitch = rawPitch.transpose(octave*12);
        if (!(isPlaying.contains(pitch))) {
            midi.beginNote(pitch.toMidiFrequency(), instrument);
            isPlaying.add(pitch);
        }
        if (isRecording){
            //NoteEvent newEvent = new NoteEvent(pitch, new Date().getTime(), instrument, NoteEvent.Kind.start);
            NoteEvent newEvent = new NoteEvent(pitch, System.currentTimeMillis(), instrument, NoteEvent.Kind.start);
            recording.add(newEvent);
        }
    }

    /**
     * Start a note.
     * When such a key is released, a note should end if it is currently sounding.
     *
     * @param rawPitch: pitch with value corresponding to 12 pitches in an octave, 0 --> 'C'
     */
    public void endNote(Pitch rawPitch) {
        Pitch pitch = rawPitch.transpose(octave*12);
        if (isPlaying.contains(pitch)) {
            midi.endNote(pitch.toMidiFrequency(), instrument);
            isPlaying.remove(pitch);
        }
        if (isRecording){
            //NoteEvent newEvent = new NoteEvent(pitch, new Date().getTime(), instrument, NoteEvent.Kind.stop);
            NoteEvent newEvent = new NoteEvent(pitch, System.currentTimeMillis(), instrument, NoteEvent.Kind.stop);
            recording.add(newEvent);
        }
    }

    /**
     * Switch to the next instrument on list midi.Instrument on pressing 'I'.
     *
     */
    public void changeInstrument() {
       	instrument = instrument.next();
    }


    /**
     * On pressing the 'V' keys should shift the notes that the keys play up by one octave (12 semitones).
     * We should be able to shift by two octaves, maximum.
     *
     */
    public void shiftUp() {
        if (octave <=1) {
            octave += 1;
        }
    }

    /**
     * On pressing the 'C' keys should shift the notes that the keys play down by one octave (12 semitones).
     * We should be able to shift by two octaves, maximum.
     *
     */
    public void shiftDown() {
    	if (octave >= -1){
    	    octave -= 1;
        }
    }

    /**
     * 'R' should toggle record mode on and off.
     * When you make a new recording it should replace the previous one.
     */
    public boolean toggleRecording() {
        isRecording = !isRecording;
        if (isRecording) {
            recording = new ArrayList<NoteEvent>();
            //timestamp = new Date().getTime();
        }
        return isRecording;
    }

    /**
     * 'P' should trigger playback.
     *
     */
    public void playback() {
        /*
        for (NoteEvent e: recording) {
            long timeDiff = ((e.getTime() - timestamp)/10);
            midi.rest((int) timeDiff);
            if (e.getKind() == NoteEvent.Kind.start){
                midi.beginNote(e.getPitch().toMidiFrequency(), e.getInstr());
            } else {
                midi.endNote(e.getPitch().toMidiFrequency(), e.getInstr());
            }
            timestamp = e.getTime();
        }
        */
        public void playback() {
            long timestamp = 0;
            
            for (NoteEvent e: recording) {
                
                if (timestamp>0) {
                    Midi.rest((int)Math.round((e.getTime() - timestamp)/10.0));
                }
                
                timestamp = e.getTime();
                
                if (e.getKind() == music.NoteEvent.Kind.start) {
                    midi.beginNote(e.getPitch().toMidiFrequency(), e.getInstr());
                } else if (e.getKind() == music.NoteEvent.Kind.stop) {
                    midi.endNote(e.getPitch().toMidiFrequency(),e.getInstr());
                }
            }
        }
    }

}
