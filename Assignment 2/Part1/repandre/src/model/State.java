package model;

/**
 * Created by etien on 19/10/2017.
 */
public enum State {
    READY, // Ready to listen standard messages
    INIT, // Waiting for welcome messages to get the balance of everyone
    IN_QUEUE // The replica pull and execute request who arrived during the init phase
}
