Feature: View all maintenance tickets including status
As manager, I want to review all maintenance tickets in the system with their status.

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
      | id | ticketRaiser   | raisedOnDate | description                      | assetNumber | status     | fixedByEmail | timeToResolve    | priority | approvalRequired |
      |  1 | manager@ap.com |   2023-07-20 | The bed needs replacement        |           2 | Assigned   | smith@ap.com | lessThanADay     | Low      | true             |
      |  2 | smith@ap.com   |   2023-07-10 | The lamp is sometimes flickering |           1 | InProgress | smith@ap.com | oneToThreeDays   | Low      | true             |
      |  3 |                |   2023-07-20 | It is noisy                      |             | Open       |              |                  |          |                  |
      |  4 | smith@ap.com   |   2023-10-15 | The lamp is broken               |           1 | Resolved   | jeff@ap.com  | oneToThreeWeeks  | Normal   | false            |
      |  5 | smith@ap.com   |   2023-10-16 | The bed needs repair             |           3 | Closed     | jeff@ap.com  | threeOrMoreWeeks | Urgent   | true             |
    Given the following notes exist in the system
      | noteTaker      | ticketId | addedOnDate | description                                          |
      | jeff@ap.com    |        2 |  2023-09-01 | Light bulb needs replacement, but it is out of stock |
      | smith@ap.com   |        1 |  2023-09-10 | Bed can still be used for a while                    |
      | manager@ap.com |        1 |  2023-09-23 | Order made, waiting for delivery                     |
      | manager@ap.com |        4 |  2023-10-17 | The lamp needs further check                         |
      | manager@ap.com |        5 |  2023-10-18 | The bed is now fixed                                 |
    Given the following ticket images exist in the system
      | imageUrl                   | ticketId |
      | https://imageurl.com/i.jpg |        1 |
      | http://thisimage.com/1.png |        1 |
      | http://thisimage.com/2.png |        2 |

  Scenario: Successfully view status of all maintenance tickets
    When the manager attempts to view all maintenance tickets in the system
    Then the following maintenance tickets shall be presented
      | id | ticketRaiser   | raisedOnDate | description                   | assetName | expectLifeSpan | purchaseDate | floorNumber | roomNumber | status     | fixedByEmail | timeToResolve    | priority | approvalRequired |
      |  1 | manager@ap.com |   2023-07-20 | This is a dummy description 1 | bed       |           5000 |   2010-01-30 |          10 |         35 | Assigned   | smith@ap.com | lessThanADay     | Low      | true             |
      |  2 | smith@ap.com   |   2023-07-10 | This is a dummy description 2 | lamp      |           1800 |   2022-03-20 |           9 |         23 | InProgress | smith@ap.com | oneToThreeDays   | Low      | true             |
      |  3 | manager@ap.com |   2023-07-20 | It is noisy                   |           |                |              |             |            | Open       |              |                  |          |                  |
      |  4 | smith@ap.com   |   2023-10-15 | The lamp is broken            | lamp      |           1800 |   2022-03-20 |           9 |         23 | Resolved   | jeff@ap.com  | oneToThreeWeeks  | Normal   | false            |
      |  5 | smith@ap.com   |   2023-10-16 | The bed needs repair          | bed       |           5000 |   2010-01-30 |           1 |         35 | Closed     | jeff@ap.com  | threeOrMoreWeeks | Urgent   | true             |
    Then the ticket with id "1" shall have the following notes
      | noteTaker      | addedOnDate | description                       |
      | smith@ap.com   |  2023-09-10 | Bed can still be used for a while |
      | manager@ap.com |  2023-09-23 | Order made, waiting for delivery  |
    Then the ticket with id "2" shall have the following notes
      | noteTaker   | addedOnDate | description                                          |
      | jeff@ap.com |  2023-09-01 | Light bulb needs replacement, but it is out of stock |
    Then the ticket with id "3" shall have no notes
    Then the ticket with id "4" shall have the following notes
      | noteTaker      | addedOnDate | description                  |
      | manager@ap.com |  2023-10-17 | The lamp needs further check |
    Then the ticket with id "5" shall have the following notes
      | noteTaker      | addedOnDate | description          |
      | manager@ap.com |  2023-10-18 | The bed is now fixed |
    Then the ticket with id "1" shall have the following images
      | imageUrl                   |
      | https://imageurl.com/i.jpg |
      | http://thisimage.com/1.png |
    Then the ticket with id "2" shall have the following images
      | imageUrl                   |
      | http://thisimage.com/2.png |
    Then the ticket with id "3" shall have no images
    Then the ticket with id "4" shall have no images
    Then the ticket with id "5" shall have no images
