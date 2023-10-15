Feature: Update Manager Password (p6)
As a manager, I wish to update my password

  Background: 
    Given the following manager exists in the system (p6)
      | email          | password |
      | manager@ap.com | manager  |

  Scenario Outline: A manager updates their password successfully
    When a manager with "<email>" attempts to update their password to "<newPassword>" (p6)
    Then the manager account information will be updated and is now "<email>" and "<newPassword>" (p6)
    Then the number of managers in the system shall be "1" (p6)

    Examples: 
      | email          | newPassword |
      | manager@ap.com | P!p1        |
      | manager@ap.com | p#2P        |
      | manager@ap.com | $aA3        |

  Scenario Outline: A manager updates their password unsuccessfully
    When a manager with "<email>" attempts to update their password to "<newPassword>" (p6)
    Then the following "<error>" shall be raised (p6)
    Then the manager account information will not be updated and will keep "<email>" and "<password>" (p6)
    Then the number of managers in the system shall be "1" (p6)

    Examples: 
      | email          | password | error                                          |
      | manager@ap.com | P!p      | Password must be at least four characters long |
      | manager@ap.com | p2P2     | Password must contain one character out of !#$ |
      | manager@ap.com |          | Password cannot be empty                       |
      | manager@ap.com | !2P2     | Password must contain one lower-case character |
      | manager@ap.com | !2p2     | Password must contain one upper-case character |
