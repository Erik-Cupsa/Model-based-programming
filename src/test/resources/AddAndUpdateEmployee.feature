Feature: Add/Update Employee (p11)
As an employee, I wish to register and update an employee account in the system

  Background: 
    Given the following employees exist in the system (p11)
      | email       | password | name | phoneNumber   |
      | jeff@ap.com | pass1    | Jeff | (555)555-5555 |
      | john@ap.com | pass2    | John | (444)444-4444 |
    Given the following manager exists in the system (p11)
      | email          | password |
      | manager@ap.com | manager  |

  Scenario Outline: An employee registers successfully
    When a new employee attempts to register with "<email>", "<password>", "<name>", and "<phoneNumber>" (p11)
    Then a new employee account shall exist with "<email>", "<password>", "<name>", and "<phoneNumber>" (p11)
    Then the number of employees in the system shall be "3" (p11)

    Examples: 
      | email       | password | name | phoneNumber   |
      | lisa@ap.com | pass4    | Lisa | (888)888-8888 |
      | liam@ap.com | pass5    | Liam | (777)777-7777 |
      | owen@ap.com | pass10   |      | (888)888-5555 |
      | noah@ap.com | pass11   | Noah |               |

  Scenario Outline: An employee registers unsuccessfully
    When a new employee attempts to register with "<email>", "<password>", "<name>", and "<phoneNumber>" (p11)
    Then the following "<error>" shall be raised (p11)
    Then the number of employees in the system shall be "2" (p11)
    Then the following employees shall exist in the system (p11)
      | email       | password | name | phoneNumber   |
      | jeff@ap.com | pass1    | Jeff | (555)555-5555 |
      | john@ap.com | pass2    | John | (444)444-4444 |

    Examples: 
      | email          | password | name | phoneNumber   | error                                       |
      | manager@ap.com | pass1    | Paul | (111)111-1111 | Email cannot be manager@ap.com              |
      | jeff@ap.com    | pass2    | Jeff | (111)777-7777 | Email already linked to an employee account |
      | bart @ ap.com  | pass3    | Bart | (444)666-6666 | Email must not contain any spaces           |
      | dony@ap@.com   | pass4    | Dony | (777)555-7777 | Invalid email                               |
      | kyle@ap.       | pass5    | Kyle | (666)777-6666 | Invalid email                               |
      | greg.ap@com    | pass6    | Greg | (777)888-7777 | Invalid email                               |
      | @ap.com        | pass7    | Otto | (111)777-6666 | Invalid email                               |
      | karl@.com      | pass8    | Karl | (111)777-6661 | Invalid email                               |
      |                | pass9    | Vino | (777)888-5555 | Email cannot be empty                       |
      | jeff@yahoo.com | pass11   | Jeff | (111)111-1111 | Email domain must be @ap.com                |
      | luke@ap.com    |          | Luke | (999)888-5555 | Password cannot be empty                    |

  Scenario Outline: An employee updates their info successfully
    When the employee with "<email>" attempts to update their account information to "<newPassword>", "<newName>", and "<newPhoneNumber>" (p11)
    Then their employee account information will be updated and is now "<email>", "<newPassword>", "<newName>", and "<newPhoneNumber>" (p11)
    Then the number of employees in the system shall be "2" (p11)

    Examples: 
      | email       | newPassword | newName | newPhoneNumber |
      | jeff@ap.com | pass5       | Jake    | (111)111-1111  |
      | john@ap.com | pass6       | Johnny  | (111)777-7777  |
      | john@ap.com | pass2       |         | (444)444-7777  |
      | john@ap.com | pass2       | Jon     |                |

  Scenario Outline: An employee updates their info unsuccessfully
    When the employee with "<email>" attempts to update their account information to "<newPassword>", "<newName>", and "<newPhoneNumber>" (p11)
    Then the following "<error>" shall be raised (p11)
    Then the number of employees in the system shall be "2" (p11)
    Then the following employees shall exist in the system (p11)
      | email       | password | name | phoneNumber   |
      | jeff@ap.com | pass1    | Jeff | (555)555-5555 |
      | john@ap.com | pass2    | John | (444)444-4444 |

    Examples: 
      | email       | password | name | phoneNumber   | newPassword | newName | newPhoneNumber | error                    |
      | jeff@ap.com | pass1    | Jeff | (555)555-5555 |             | Jeff    | (555)666-5555  | Password cannot be empty |
