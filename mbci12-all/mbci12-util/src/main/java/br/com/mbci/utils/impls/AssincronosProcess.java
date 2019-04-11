/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mbci.utils.impls;

import java.awt.EventQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AssincronosProcess {

    private static final Queue<Runnable> QUEUE = new ConcurrentLinkedQueue<Runnable>();
    private static boolean emExecucao = false;

    private AssincronosProcess() {
    }

    public static void execute(Runnable processo) {
        if (!emExecucao) {
            initThread();
        }
        synchronized (QUEUE) {
            QUEUE.offer(processo);
            QUEUE.notify();
        }
    }

    public static void destroyThread() {
        emExecucao = false;
        synchronized (QUEUE) {
            QUEUE.notify();
        }
    }

    private static void initThread() {
        emExecucao = true;
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (emExecucao) {
                            synchronized (QUEUE) {
                                try {
                                    while (emExecucao && QUEUE.isEmpty()) {
                                        QUEUE.wait();
                                    }
                                    Runnable exec = QUEUE.poll();
                                    if (exec != null) {
                                        exec.run();
                                    }
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(AssincronosProcess.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (Throwable ex) {
                                    Logger.getLogger(AssincronosProcess.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                }, "Thread-AssincronosProcess").start();
            }
        });
    }
}
