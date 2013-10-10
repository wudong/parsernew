package uk.ac.ebi.uniprot.parser.tool;

import com.google.common.util.concurrent.*;
import uk.ac.ebi.uniprot.parser.impl.entry.EntryObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 *
 * A multi-thread FF parser.
 *
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 09/10/2013
 * Time: 10:11
 * To change this template use File | Settings | File Templates.
 */
public class MTFFParser {

    final private EntryBufferedReader reader;

    //process the parsing job.
    final private ListeningExecutorService parsingExecutor;
    //process the listener job.
    final private ExecutorService listenerExecutor;
    private List<EntryObjectListener> listenerList = new ArrayList<EntryObjectListener>();

    public MTFFParser(EntryBufferedReader reader){
        this.reader = reader;

        final LimitedQueue<Runnable> internalQueue = new LimitedQueue<Runnable>(1000);
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5,
                0l, TimeUnit.MILLISECONDS, internalQueue);

        parsingExecutor = MoreExecutors.listeningDecorator(threadPoolExecutor);
        listenerExecutor = Executors.newFixedThreadPool(5);
    }

    public void addListener(EntryObjectListener listener ){
        listenerList.add(listener);
    }

    public void start() throws IOException {
        String next = reader.next();
        while (next!=null){
            ParsingJob parsingJob = new ParsingJob(next);
            ListenableFuture<EntryObject> submit = parsingExecutor.submit(parsingJob);
            Futures.addCallback(submit, new FutureCallback<EntryObject>() {
                @Override
                public void onSuccess(EntryObject entryObject) {
                    //System.out.println("Parsed: "+ entryObject.id.entryName);
                     for (EntryObjectListener listener: listenerList){
                         ListenerJob listenerJob = new ListenerJob(listener, entryObject);
                         listenerExecutor.submit(listenerJob);
                     }
                }

                @Override
                public void onFailure(Throwable throwable) {
                    //
                }
            });

            next=reader.next();
        }
    }


    public void shutdown() throws InterruptedException {
        this.parsingExecutor.shutdown();
        this.parsingExecutor.awaitTermination(1, TimeUnit.DAYS);
        this.listenerExecutor.shutdown();
        this.listenerExecutor.awaitTermination(1, TimeUnit.DAYS);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        EntryBufferedReader reader1 = new EntryBufferedReader(args[0]);
        MTFFParser mtffParser = new MTFFParser(reader1);
        mtffParser.start();
        mtffParser.shutdown();
    }
}

class LimitedQueue<E> extends ArrayBlockingQueue<E> {
    public LimitedQueue(int maxSize) {
        super(maxSize);
    }

    @Override
    public boolean offer(E e) {
        // turn offer() and add() into a blocking calls (unless interrupted)
        try {
            put(e);
            return true;
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        return false;
    }

}
