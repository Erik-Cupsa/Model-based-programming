Feature: Delete Maintenance Ticket (p7)
As a manager, I want to delete a maintenance ticket so that it is no longer available.

  Background: 
    Given the following employees exist in the system (p7)
      | email        | password | name  | phoneNumber   |
      | jeff@ap.com  | pass1    | Jeff  | (555)555-5555 |
      | smith@ap.com | pass2    | Smith | (555)555-5555 |
    Given the following manager exists in the system (p7)
      | email          | password |
      | manager@ap.com | manager  |
    Given the following asset types exist in the system (p7)
      | name | expectedLifeSpan |
      | lamp |             1800 |
      | bed  |             5000 |
    Given the following assets exist in the system (p7)
      | assetNumber | type | purchaseDate | floorNumber | roomNumber |
      |           1 | lamp |   2022-03-20 |           9 |         23 |
      |           2 | bed  |   2010-01-30 |          10 |         35 |
    Given the following tickets exist in the system (p7)
      | id | ticketRaiser   | raisedOnDate | description                   | assetNumber |
      |  1 | manager@ap.com |   2023-07-20 | This is a dummy description 1 |           2 |
      |  2 | smith@ap.com   |   2023-07-10 | This is a dummy description 2 |           1 |

  Scenario: Successfully delete a maintenance ticket
    When the manager attempts to delete the maintenance ticket with id "2" (p7)
    Then the number of maintenance tickets in the system shall be "1" (p7)
    Then the following tickets shall exist in the system (p7)
      | id | ticketRaiser   | raisedOnDate | description                   | assetNumber |
      |  1 | manager@ap.com |   2023-07-20 | This is a dummy description 1 |           2 |

  Scenario: Successfully delete a maintenance ticket that does not exist in the system
    When the manager attempts to delete the maintenance ticket with id "3" (p7)
    Then the number of maintenance tickets in the system shall be "2" (p7)
    Then the following tickets shall exist in the system (p7)
      | id | ticketRaiser   | raisedOnDate | description                   | assetNumber |
      |  1 | manager@ap.com |   2023-07-20 | This is a dummy description 1 |           2 |
      |  2 | smith@ap.com   |   2023-07-10 | This is a dummy description 2 |           1 |
