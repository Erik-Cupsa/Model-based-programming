Feature: Delete Guest (p8)
As an guest, I wish to delete my guest account

  Background: 
    Given the following guests exist in the system (p8)
      | email          | password | name | phoneNumber   |
      | jeff@yahoo.com | pass1    | Jeff | (555)555-5555 |
      | john@gmail.com | pass2    | John | (444)444-4444 |
    Given the following manager exists in the system (p8)
      | email          | password |
      | manager@ap.com | manager  |

  Scenario Outline: Successfully deleting a guest
    When the guest attempts to delete their own account linked to the "<email>" (p8)
    Then the guest account linked to "<email>" shall not exist in the system (p8)
    Then the number of guests in the system shall be "<numberOfGuests>" (p8)

    Examples: 
      | email          | numberOfGuests |
      | jeff@yahoo.com |              1 |
      | john@gmail.com |              1 |
      | kyle@yahoo.com |              2 |
      | paul@yahoo.com |              2 |

  Scenario: Successfully deleting a guest that does not exist but manager exists
    When the guest attempts to delete their own account linked to the "manager@ap.com" (p8)
    Then the guest account linked to "manager@ap.com" shall not exist in the system (p8)
    Then the manager account linked to "manager@ap.com" shall exist in the system (p8)
    Then the number of guests in the system shall be "2" (p8)
