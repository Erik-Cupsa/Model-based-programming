Feature: Delete Note (p2)
As a manager, I want to delete a maintenance note for a ticket so that it is no longer available.

  Background: 
    Given the following employees exist in the system (p2)
      | email        | password | name  | phoneNumber   |
      | jeff@ap.com  | pass1    | Jeff  | (555)555-5555 |
      | smith@ap.com | pass2    | Smith | (555)555-5555 |
    Given the following manager exists in the system (p2)
      | email          | password |
      | manager@ap.com | manager  |
    Given the following asset types exist in the system (p2)
      | name | expectedLifeSpan |
      | lamp |             1800 |
      | bed  |             5000 |
    Given the following assets exist in the system (p2)
      | assetNumber | type | purchaseDate | floorNumber | roomNumber |
      |           1 | lamp |   2022-03-20 |           9 |         23 |
      |           2 | bed  |   2010-01-30 |          10 |         35 |
    Given the following tickets exist in the system (p2)
      | id | ticketRaiser   | raisedOnDate | description                   | assetNumber |
      |  1 | manager@ap.com |   2023-07-20 | This is a dummy description 1 |           2 |
      |  2 | smith@ap.com   |   2023-07-10 | This is a dummy description 2 |           1 |
    Given the following notes exist in the system (p2)
      | noteTaker      | ticketId | addedOnDate | description                               |
      | jeff@ap.com    |        2 |  2023-09-01 | This is a dummy note 1 for a ticket       |
      | smith@ap.com   |        1 |  2023-09-10 | This is another dummy note 2 for a ticket |
      | manager@ap.com |        1 |  2023-09-23 | This is a dummy description 1             |

  Scenario Outline: Successfully delete a note for a maintenance ticket in the system
    When the manger attempts to delete note number "<noteIndex>" for maintenance ticket "<ticketId>" (p2)
    Then the number of notes in the system shall be "2" (p2)
    Then the number of notes for ticket "<ticketId>" in the system shall be "<numberOfNotes>" (p2)
    Then the note number "<noteIndex>" for ticket <ticketId> shall not exist in the system (p2)

    Examples: 
      | noteIndex | ticketId | numberOfNotes |
      |         0 |        2 |             0 |
      |         0 |        1 |             1 |
      |         1 |        1 |             1 |

  Scenario Outline: Successfully delete a nonexisting note for a maintenance ticket in the system
    When the manger attempts to delete note number "<noteIndex>" for maintenance ticket "<ticketId>" (p2)
    Then the number of notes in the system shall be "3" (p2)
    Then the number of notes for ticket "<ticketId>" in the system shall be "<numberOfNotes>" (p2)
    Then the note number "<noteIndex>" for ticket <ticketId> shall not exist in the system (p2)

    Examples: 
      | noteIndex | ticketId | numberOfNotes |
      |         1 |        2 |             1 |
      |         2 |        1 |             2 |
      |         3 |        1 |             2 |

  Scenario Outline: Successfully delete a nonexisting note for a nonexisting maintenance ticket in the system
    When the manger attempts to delete note number "<noteIndex>" for maintenance ticket "<ticketId>" (p2)
    Then the number of notes in the system shall be "3" (p2)

    Examples: 
      | noteIndex | ticketId |
      |         0 |        3 |
