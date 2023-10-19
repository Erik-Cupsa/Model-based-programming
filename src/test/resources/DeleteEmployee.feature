Feature: Delete Employee (p1)
As an employee, I wish to delete my employee account

  Background: 
    Given the following employees exist in the system (p1)
      | email       | password | name | phoneNumber   |
      | jeff@ap.com | pass1    | Jeff | (555)555-5555 |
      | john@ap.com | pass2    | John | (444)444-4444 |
    Given the following manager exists in the system (p1)
      | email          | password |
      | manager@ap.com | manager  |

  Scenario Outline: Successfully deleting a employee
    When the employee attempts to delete their own account linked to the "<email>" (p1)
    Then the employee account linked to "<email>" shall not exist in the system (p1)
    Then the number of employees in the system shall be "<numberOfEmployees>" (p1)

    Examples: 
      | email       | numberOfEmployees |
      | jeff@ap.com |                 1 |
      | john@ap.com |                 1 |
      | kyle@ap.com |                 2 |
      | paul@ap.com |                 2 |

  Scenario: Successfully deleting an employee that does not exist but manager exists
    When the employee attempts to delete their own account linked to the "manager@ap.com" (p1)
    Then the employee account linked to "manager@ap.com" shall not exist in the system (p1)
    Then the manager account linked to "manager@ap.com" shall exist in the system (p1)
    Then the number of employees in the system shall be "2" (p1)
