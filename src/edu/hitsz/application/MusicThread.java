package edu.hitsz.application;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicThread extends Thread {

    /**
     * 音频文件名
     */
    private String videos;
    private AudioFormat audioFormat;
    private byte[] samples;

    /**
     * 播放/停止标志
     */
    private boolean isStop = false;
    /**
     * 循环播放标志
     */
    private boolean inLoop = false;
    /**
     * 借宿播放标志
     */
    private boolean toEnd = false;

    /**
     * 结束线程标志
     */
    private boolean running = true;

    /**
     * 重新播放标志
     */
    private boolean rePlay = false;



    public MusicThread(String filename,boolean isStop,boolean inLoop) {
        //初始化filename
        this.videos = filename;
        this.isStop = isStop;
        this.inLoop = inLoop;
        reverseMusic();
    }

    public void reverseMusic() {
        try {
            //定义一个AudioInputStream用于接收输入的音频数据，使用AudioSystem来获取音频的音频输入流
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(videos));
            //用AudioFormat来获取AudioInputStream的格式
            audioFormat = stream.getFormat();
            samples = getSamples(stream);
        } catch (UnsupportedAudioFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public byte[] getSamples(AudioInputStream stream) {
        int size = (int) (stream.getFrameLength() * audioFormat.getFrameSize());
        byte[] samples = new byte[size];
        DataInputStream dataInputStream = new DataInputStream(stream);
        try {
            dataInputStream.readFully(samples);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return samples;
    }

    public void play(InputStream source) {
        int size = (int) (audioFormat.getFrameSize() * audioFormat.getSampleRate());
        byte[] buffer = new byte[size];
        //源数据行SoureDataLine是可以写入数据的数据行
        SourceDataLine dataLine = null;
        //获取受数据行支持的音频格式DataLine.info
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            dataLine = (SourceDataLine) AudioSystem.getLine(info);
            dataLine.open(audioFormat, size);
        } catch (LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dataLine.start();
        try {
            int numBytesRead = 0;
            while (numBytesRead != -1) {
                //结束
                if(toEnd){
                    break;
                }
                //重新从开头播放
                if(rePlay){
                    rePlay = false;
                    break;
                }
                //暂停
                if(isStop){
                    while (isStop){
                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
				//从音频流读取指定的最大数量的数据字节，并将其放入缓冲区中
                numBytesRead =
                        source.read(buffer, 0, buffer.length);
				//通过此源数据行将数据写入混频器
                if (numBytesRead != -1) {
                    dataLine.write(buffer, 0, numBytesRead);
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        dataLine.drain();
        dataLine.close();

    }


    /**
     * 设置音效打开/关闭
     */
    public void setStop(boolean isStop){this.isStop=isStop;}

    /**
     * 设置循环播放
     */
    public void setInLoop(boolean inLoop){this.inLoop = inLoop;}

    /**
     * 结束播放
     */
    public void setToEnd(boolean toEnd){this.toEnd = toEnd;}

    /**
     * 设置结束线程标志
     * @param running 结束线程标志
     */
    public void setRunning(boolean running){this.running = running;}

    public void setRePlay(boolean rePlay){this.rePlay = rePlay;}

    /**
     * 控制音频循环播放
     */
    @Override
    public void run() {
        if (!isStop) {
            if (inLoop) {
                while (inLoop) {
                    InputStream stream = new ByteArrayInputStream(samples);
                    play(stream);
                    if (!running) {
                        return;
                    }
                }
            }
            InputStream stream = new ByteArrayInputStream(samples);
            play(stream);
        }
    }
}


