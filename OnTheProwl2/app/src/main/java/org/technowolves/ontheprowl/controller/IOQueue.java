package org.technowolves.ontheprowl.controller;

import org.technowolves.ontheprowl.model.IOOperation;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Class to queue file input/output operations to
 * save resources
 *
 * @author Hamzah Aslam
 */

public class IOQueue {

    /** Queue of operations */
    private Queue<IOOperation> queue;

    /**
     * Constructor for IOQueue class
     */
    public IOQueue() {
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
    public boolean dequeue(int index) {
        return queue.remove(index);
    }

    /**
     * Remove an IO operation to the queue
     *
     * @param operation The operation to remove
     * @return True if the operation has been removed
     *         successfully and false otherwise
     */
    public boolean dequeue(IOOperation operation) {
        return queue.remove(operation);
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
            if (o.execute()) dequeue(o);
            else all = false;
        }

        // return boolean
        return all;
    }

}
