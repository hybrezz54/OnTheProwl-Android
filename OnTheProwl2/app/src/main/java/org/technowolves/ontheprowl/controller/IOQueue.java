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
     * successfully and False otherwise
     */
    public boolean enqueue(IOOperation operation) {
        return queue.add(operation);
    }

}
