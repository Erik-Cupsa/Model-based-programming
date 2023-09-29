Feature: View status of all maintenance tickets (p15)
As manager, I want to review all maintenance tickets in the system.

  Background: 
    Given the following employees exist in the system (p15)
      | email        | password | name  | phoneNumber   |
      | jeff@ap.com  | pass1    | Jeff  | (555)555-5555 |
      | smith@ap.com | pass2    | Smith | (555)555-5555 |
    Given the following manager exists in the system (p15)
      | email          | password |
      | manager@ap.com | manager  |
    Given the following asset types exist in the system (p15)
      | name | expectedLifeSpan |
      | lamp |             1800 |
      | bed  |             5000 |
    Given the following assets exist in the system (p15)
      | assetNumber | type | purchaseDate | floorNumber | roomNumber |
      |           1 | lamp |   2022-03-20 |           9 |         23 |
      |           2 | bed  |   2010-01-30 |          10 |         35 |
      |           3 | bed  |   2010-01-30 |           1 |         35 |
    Given the following tickets exist in the system (p15)
      | id | ticketRaiser   | raisedOnDate | description                   | assetNumber |
      |  1 | manager@ap.com |   2023-07-20 | This is a dummy description 1 |           2 |
      |  2 | smith@ap.com   |   2023-07-10 | This is a dummy description 2 |           1 |
      |  3 | manager@ap.com |   2023-07-20 | It is noisy                   |             |
    Given the following notes exist in the system (p15)
      | noteTaker      | ticketId | addedOnDate | description                               |
      | jeff@ap.com    |        2 |  2023-09-01 | This is a dummy note 1 for a ticket       |
      | smith@ap.com   |        1 |  2023-09-10 | This is another dummy note 2 for a ticket |
      | manager@ap.com |        1 |  2023-09-23 | This is a dummy description 1             |
    Given the following ticket images exist in the system (p15)
      | imageUrl                   | ticketId |
      | https://imageurl.com/i.jpg |        1 |
      | http://thisimage.com/1.png |        1 |
      | http://thisimage.com/2.png |        2 |

  Scenario: Successfully view status of all maintenance tickets
    When the manager attempts to view all maintenance tickets in the system (p15)
    Then the following maintenance tickets shall be presented (p15)
      | id | ticketRaiser   | raisedOnDate | description                   | assetName | expectLifeSpan | purchaseDate | floorNumber | roomNumber |
      |  1 | manager@ap.com |   2023-07-20 | This is a dummy description 1 | bed       |           5000 |   2010-01-30 |          10 |         35 |
      |  2 | smith@ap.com   |   2023-07-10 | This is a dummy description 2 | lamp      |           1800 |   2022-03-20 |           9 |         23 |
      |  3 | manager@ap.com |   2023-07-20 | It is noisy                   |           |                |              |             |            |
    Then the ticket with id "1" shall have the following notes (p15)
      | noteTaker      | addedOnDate | description                               |
      | smith@ap.com   |  2023-09-10 | This is another dummy note 2 for a ticket |
      | manager@ap.com |  2023-09-23 | This is a dummy description 1             |
    Then the ticket with id "2" shall have the following notes (p15)
      | noteTaker   | addedOnDate | description                         |
      | jeff@ap.com |  2023-09-01 | This is a dummy note 1 for a ticket |
    Then the ticket with id "3" shall have no notes (p15)
    Then the ticket with id "1" shall have the following images (p15)
      | imageUrl                   |
      | https://imageurl.com/i.jpg |
      | http://thisimage.com/1.png |
    Then the ticket with id "2" shall have the following images (p15)
      | imageUrl                   |
      | http://thisimage.com/2.png |
    Then the ticket with id "3" shall have no images (p15)
