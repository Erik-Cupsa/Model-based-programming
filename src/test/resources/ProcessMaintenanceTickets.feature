Feature: Process maintenance tickets
As manager, I want to process maintenance tickets to keep their status up-to-date.

  Background: 
    Given the following employees exist in the system
      | email        | password | name  | phoneNumber   |
      | jeff@ap.com  | pass1    | Jeff  | (555)555-5555 |
      | smith@ap.com | pass2    | Smith | (555)555-5555 |
    Given the following manager exists in the system
      | email          | password |
      | manager@ap.com | manager  |
    Given the following asset types exist in the system
      | name | expectedLifeSpan |
      | lamp |             1800 |
      | bed  |             5000 |
    Given the following assets exist in the system
      | assetNumber | type | purchaseDate | floorNumber | roomNumber |
      |           1 | lamp |   2022-03-20 |           9 |         23 |
      |           2 | bed  |   2010-01-30 |          10 |         35 |
      |           3 | bed  |   2010-01-30 |           1 |         35 |
    Given the following tickets exist in the system
      | id | ticketRaiser   | raisedOnDate | description                      | assetNumber | status |
      |  1 | manager@ap.com |   2023-07-20 | The bed needs replacement        |           2 | Open   |
      |  2 | jeff@ap.com    |   2023-07-10 | The lamp is sometimes flickering |           1 | Open   |

  Scenario Outline: Successfully assign a maintenance ticket
    When the manager attempts to assign the ticket "<ticketId>" to "<employeeEmail>" with estimated time "<timeEstimate>", priority "<priority>", and requires approval "<requiresApproval>"
    Then the ticket "<ticketId>" shall be marked as "Assigned"
    Then the ticket "<ticketId>" shall be assigned to "<employeeEmail>"
    Then the ticket "<ticketId>" shall have estimated time "<timeEstimate>", priority "<priority>", and requires approval "<requiresApproval>"

    Examples: 
      | ticketId | employeeEmail | timeEstimate     | priority | requiresApproval |
      |        1 | smith@ap.com  | ThreeToSevenDays | Low      | false            |
      |        2 | jeff@ap.com   | OneToThreeDays   | High     | true             |

  Scenario Outline: Unsuccesfully assign a maintenance ticket due to non-existing employee
    When the manager attempts to assign the ticket "<ticketId>" to "<employeeEmail>" with estimated time "<timeEstimate>", priority "<priority>", and requires approval "<requiresApproval>"
    Then the ticket "<ticketId>" shall be marked as "Open"
    Then the system shall raise the error "<error>"

    Examples: 
      | ticketId | employeeEmail    | timeEstimate     | priority | requiresApproval | error                           |
      |        1 | not_exist@ap.com | ThreeToSevenDays | Low      | false            | Staff to assign does not exist. |

  Scenario Outline: Unsuccesfully assign a maintenance ticket due to wrong input
    When the manager attempts to assign the ticket "<ticketId>" to "smith@ap.com" with estimated time "ThreeToSevenDays", priority "Low", and requires approval "false"
    Then the ticket "<ticketId>" shall not exist in the system
    Then the number of tickets in the system shall be "2"
    Then the system shall raise the error "<error>"

    Examples: 
      | ticketId | error                              |
      |        3 | Maintenance ticket does not exist. |
      |        4 | Maintenance ticket does not exist. |

  Scenario Outline: Unsuccesfully assign a maintenance ticket due to wrong state
    Given ticket "1" is marked as "<state>"
    When the manager attempts to assign the ticket "1" to "smith@ap.com" with estimated time "ThreeToSevenDays", priority "Low", and requires approval "false"
    Then the ticket "1" shall be marked as "<state>"
    Then the system shall raise the error "<error>"

    Examples: 
      | state      | error                                                    |
      | Assigned   | The maintenance ticket is already assigned.              |
      | Resolved   | Cannot assign a maintenance ticket which is resolved.    |
      | Closed     | Cannot assign a maintenance ticket which is closed.      |
      | InProgress | Cannot assign a maintenance ticket which is in progress. |

  Scenario Outline: Successfully start an assigned maintenance ticket
    Given ticket "<ticketId>" is marked as "Assigned"
    When the hotel staff attempts to start the ticket "<ticketId>"
    Then the ticket "<ticketId>" shall be marked as "InProgress"

    Examples: 
      | ticketId |
      |        1 |
      |        2 |

  Scenario Outline: Unsuccesfully start a maintenance ticket due to wrong input
    When the hotel staff attempts to start the ticket "<ticketId>"
    Then the ticket "<ticketId>" shall not exist in the system
    Then the number of tickets in the system shall be "2"
    Then the system shall raise the error "<error>"

    Examples: 
      | ticketId | error                              |
      |        3 | Maintenance ticket does not exist. |
      |        4 | Maintenance ticket does not exist. |

  Scenario Outline: Unsuccesfully start a maintenance ticket due to wrong state
    Given ticket "1" is marked as "<state>"
    When the hotel staff attempts to start the ticket "1"
    Then the ticket "1" shall be marked as "<state>"
    Then the system shall raise the error "<error>"

    Examples: 
      | state      | error                                                |
      | Open       | Cannot start a maintenance ticket which is open.     |
      | Resolved   | Cannot start a maintenance ticket which is resolved. |
      | Closed     | Cannot start a maintenance ticket which is closed.   |
      | InProgress | The maintenance ticket is already in progress.       |

  Scenario Outline: Successfully complete a maintenance ticket
    Given ticket "<ticketId>" is marked as "InProgress" with requires approval "<requiresApproval>"
    When the hotel staff attempts to complete the ticket "<ticketId>"
    Then the ticket "<ticketId>" shall be marked as "<state>"

    Examples: 
      | ticketId | requiresApproval | state    |
      |        1 | false            | Closed   |
      |        2 | true             | Resolved |
      |        1 | true             | Resolved |
      |        2 | false            | Closed   |

  Scenario Outline: Unsuccesfully complete a maintenance ticket due to wrong input
    When the hotel staff attempts to complete the ticket "<ticketId>"
    Then the ticket "<ticketId>" shall not exist in the system
    Then the number of tickets in the system shall be "2"
    Then the system shall raise the error "<error>"

    Examples: 
      | ticketId | error                              |
      |        3 | Maintenance ticket does not exist. |
      |        4 | Maintenance ticket does not exist. |

  Scenario Outline: Unsuccesfully complete a maintenance ticket due to wrong state
    Given ticket "1" is marked as "<state>"
    When the hotel staff attempts to complete the ticket "1"
    Then the ticket "1" shall be marked as "<state>"
    Then the system shall raise the error "<error>"

    Examples: 
      | state    | error                                                   |
      | Open     | Cannot complete a maintenance ticket which is open.     |
      | Assigned | Cannot complete a maintenance ticket which is assigned. |
      | Closed   | The maintenance ticket is already closed.               |
      | Resolved | The maintenance ticket is already resolved.             |

  Scenario Outline: Successfully disapprove a maintenance ticket
    Given ticket "<ticketId>" is marked as "Resolved"
    When the manager attempts to disapprove the ticket "<ticketId>" on date "2023-10-15" and with reason "Disapprove! Redo!"
    Then the ticket "<ticketId>" shall be marked as "InProgress"
    Then the ticket with id "<ticketId>" shall have the following notes
      | noteTaker      | addedOnDate | description       |
      | manager@ap.com |  2023-10-15 | Disapprove! Redo! |

    Examples: 
      | ticketId |
      |        1 |
      |        2 |

  Scenario Outline: Unsuccesfully disapprove a maintenance ticket due to wrong input
    When the manager attempts to disapprove the ticket "<ticketId>" on date "2023-10-15" and with reason "Disapprove! Redo!"
    Then the ticket "<ticketId>" shall not exist in the system
    Then the number of tickets in the system shall be "2"
    Then the system shall raise the error "<error>"

    Examples: 
      | ticketId | error                              |
      |        3 | Maintenance ticket does not exist. |
      |        4 | Maintenance ticket does not exist. |

  Scenario Outline: Unsuccesfully disapprove a maintenance ticket due to wrong state
    Given ticket "1" is marked as "<state>"
    When the manager attempts to disapprove the ticket "1" on date "2023-10-15" and with reason "Disapprove! Redo!"
    Then the ticket "1" shall be marked as "<state>"
    Then the system shall raise the error "<error>"

    Examples: 
      | state      | error                                                        |
      | Open       | Cannot disapprove a maintenance ticket which is open.        |
      | Assigned   | Cannot disapprove a maintenance ticket which is assigned.    |
      | Closed     | Cannot disapprove a maintenance ticket which is closed.      |
      | InProgress | Cannot disapprove a maintenance ticket which is in progress. |

  Scenario Outline: Successfully approve a maintenance ticket
    Given ticket "<ticketId>" is marked as "Resolved"
    When the manager attempts to approve the ticket "<ticketId>"
    Then the ticket "<ticketId>" shall be marked as "Closed"

    Examples: 
      | ticketId |
      |        1 |
      |        2 |

  Scenario Outline: Unsuccesfully approve a maintenance ticket due to wrong input
    When the manager attempts to approve the ticket "<ticketId>"
    Then the ticket "<ticketId>" shall not exist in the system
    Then the number of tickets in the system shall be "2"
    Then the system shall raise the error "<error>"

    Examples: 
      | ticketId | error                              |
      |        3 | Maintenance ticket does not exist. |
      |        4 | Maintenance ticket does not exist. |

  Scenario Outline: Unsuccesfully approve a maintenance ticket due to wrong state
    Given ticket "1" is marked as "<state>"
    When the manager attempts to approve the ticket "1"
    Then the ticket "1" shall be marked as "<state>"
    Then the system shall raise the error "<error>"

    Examples: 
      | state      | error                                                     |
      | Open       | Cannot approve a maintenance ticket which is open.        |
      | Assigned   | Cannot approve a maintenance ticket which is assigned.    |
      | Closed     | The maintenance ticket is already closed.                 |
      | InProgress | Cannot approve a maintenance ticket which is in progress. |
