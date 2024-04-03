
import lejos.hardware.Sound;

public class SingSongs implements Runnable {
    // 音乐片段
    private int[] notes = {440, 523, 659, 784, 880, 784, 659, 523};
    private int[] durations = {500, 500, 500, 500, 500, 500, 500, 500};

    @Override
    public void run() {
        // 计算音乐的总时长
        int totalDuration = 0;
        for (int duration : durations) {
            totalDuration += duration;
        }

        // 循环播放音乐直到停止
        long startTime = System.currentTimeMillis();
        long currentTime = startTime;
        while (currentTime - startTime < totalDuration) {
            for (int i = 0; i < notes.length; i++) {
                Sound.playTone(notes[i], durations[i]);
                try {
                    Thread.sleep(durations[i]); // 等待音符持续时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentTime = System.currentTimeMillis();
            }
        }
    }
}
