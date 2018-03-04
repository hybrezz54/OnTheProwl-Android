package com.hybrez.ontheprowl.util;

import com.hybrez.ontheprowl.model.IOOperation;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A class designed to queue file input/output operations to
 * save resources. The class enforces a singleton design pattern.
 *
 * @author Hamzah Aslam
 */
public class IOQueue {

    /** IOQueue single instance */
    private static IOQueue mInstance;

    /** Queue of operations */
    private Queue<IOOperation> queue;

    /**
     * Return an instance of the IOQueue
     *
     * @return An instance of the IOQueue
     */
    public static IOQueue getInstance() {
        if (mInstance == null)
            mInstance = new IOQueue();

        return mInstance;
    }

    /**
     * Constructor for IOQueue class
     */
    private IOQueue() {
        queue = new LinkedList<>();
    }

    /**
     * Add an IO operation to the queue
     *
     * @param operation The operation to add
     * @return True if the operation has been added
     *         successfully and false otherwise
     */
    public boolean enqueue(IOOperation operation) {
        return queue.add(operation);
    }

    /**
     * Remove an IO operation to the queue
     *
     * @param index The index of the operation to remove
     * @return True if the operation has been removed
     *         successfully and false otherwise
     */
    public boolean cancel(int index) {
        return queue.remove(index);
    }

    /**
     * Remove an IO operation to the queue
     *
     * @param operation The operation to remove
     * @return True if the operation has been removed
     *         successfully and false otherwise
     */
    public boolean cancel(IOOperation operation) {
        return queue.remove(operation);
    }

    /**
     * Remove the most recent added IO operation in the queue
     *
     * @return True if the operation has been removed successfully
     *         and false otherwise
     */
    public boolean cancelRecent() {
        return cancel(queue.size() - 1);
    }

    /**
     * Execute all the operations of the IOOperations contained
     * within the queue
     *
     * @return True if all of the IOOperations have successfully executed
     *         and false otherwise
     */
    public boolean executeAll() {
        // init boolean
        boolean all = true;

        // iterate over elements and execute their operations
        for (IOOperation o : new LinkedList<IOOperation>(queue)) {
            if (o.execute()) cancel(o);
            else all = false;
        }

        // return boolean
        return all;
    }

}
