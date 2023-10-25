Feature: Add/Update Guest (p10)
As a guest, I wish to register and update a guest account in the system

  Background: 
    Given the following guests exist in the system (p10)
      | email          | password | name | phoneNumber   |
      | jeff@gmail.com | pass1    | Jeff | (555)555-5555 |
      | john@gmail.com | pass2    | John | (444)444-4444 |
    Given the following manager exists in the system (p10)
      | email          | password |
      | manager@ap.com | manager  |

  Scenario Outline: A guest registers successfully
    When a new guest attempts to register with "<email>", "<password>", "<name>", and "<phoneNumber>" (p10)
    Then a new guest account shall exist with "<email>", "<password>", "<name>", and "<phoneNumber>" (p10)
    Then the number of guests in the system shall be "3" (p10)

    Examples: 
      | email          | password | name | phoneNumber   |
      | lisa@gmail.com | pass4    | Lisa | (888)888-8888 |
      | liam@yahoo.com | pass5    | Liam | (777)777-7777 |
      | owen@gmail.com | pass10   |      | (888)888-5555 |
      | noah@gmail.com | pass11   | Noah |               |

  Scenario Outline: A guest registers unsuccessfully
    When a new guest attempts to register with "<email>", "<password>", "<name>", and "<phoneNumber>" (p10)
    Then the following "<error>" shall be raised (p10)
    Then the number of guests in the system shall be "2" (p10)
    Then the following guests shall exist in the system (p10)
      | email          | password | name | phoneNumber   |
      | jeff@gmail.com | pass1    | Jeff | (555)555-5555 |
      | john@gmail.com | pass2    | John | (444)444-4444 |

    Examples: 
      | email           | password | name | emergencyContact | error                                   |
      | manager@ap.com  | pass1    | Paul | (111)111-1111    | Email cannot be manager@ap.com          |
      | jeff@ap.com     | pass1    | Jeff | (111)111-1111    | Email domain cannot be @ap.com          |
      | jeff@gmail.com  | pass2    | Jeff | (111)777-7777    | Email already linked to a guest account |
      | bart @ ap.com   | pass3    | Bart | (444)666-6666    | Email must not contain any spaces       |
      | dony@gmail@.com | pass4    | Dony | (777)555-7777    | Invalid email                           |
      | kyle@gmail.     | pass5    | Kyle | (666)777-6666    | Invalid email                           |
      | greg.ap@com     | pass6    | Greg | (777)888-7777    | Invalid email                           |
      | @gmail.com      | pass7    | Otto | (111)777-6666    | Invalid email                           |
      | karl@.com       | pass8    | Karl | (111)777-6661    | Invalid email                           |
      |                 | pass9    | Vino | (777)888-5555    | Email cannot be empty                   |
      | luke@gmail.com  |          | Luke | (999)888-5555    | Password cannot be empty                |

  Scenario Outline: A guest updates their info successfully
    When the guest with "<email>" attempts to update their account information to "<newPassword>", "<newName>", and "<newPhoneNumber>" (p10)
    Then their guest account information will be updated and is now "<email>", "<newPassword>", "<newName>", and "<newPhoneNumber>" (p10)
    Then the number of guests in the system shall be "2" (p10)

    Examples: 
      | email          | newPassword | newName | newPhoneNumber |
      | jeff@gmail.com | pass5       | Jake    | (111)111-1111  |
      | john@gmail.com | pass6       | Johnny  | (111)777-7777  |
      | john@gmail.com | pass2       |         | (444)444-7777  |
      | john@gmail.com | pass2       | Jon     |                |

  Scenario Outline: A guest updates their info unsuccessfully
    When the guest with "<email>" attempts to update their account information to "<newPassword>", "<newName>", and "<newPhoneNumber>" (p10)
    Then the following "<error>" shall be raised (p10)
    Then the number of guests in the system shall be "2" (p10)
    Then the following guests shall exist in the system (p10)
      | email          | password | name | phoneNumber   |
      | jeff@gmail.com | pass1    | Jeff | (555)555-5555 |
      | john@gmail.com | pass2    | John | (444)444-4444 |

    Examples: 
      | email          | password | name | phoneNumber   | newPassword | newName | newPhoneNumber | error                    |
      | jeff@gmail.com | pass1    | Jeff | (555)555-5555 |             | Jeff    | (555)666-5555  | Password cannot be empty |
