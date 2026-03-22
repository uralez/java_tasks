package com.ayakovlev.interviewprep.entity;

/**
 * Represents the roles available to students in the system.
 */
public enum Role {
    USER,           // standard account: can view and register answers
    ADMIN,          // can manage all accounts and their data
    DEMO_TEMPLATE,  // holds the base dataset copied to new DEMO accounts; login is blocked in SecurityConfig
    DEMO            // temporary guest account: can view and register answers, but cannot edit or delete; expires after 24 hours
}
