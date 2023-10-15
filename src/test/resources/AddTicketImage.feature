Feature: Add ticket image (p5)
As hotel staff, I want to add a ticket image to the ticket

  Background: 
    Given the following employees exist in the system (p5)
      | email        | password | name  | phoneNumber   |
      | jeff@ap.com  | pass1    | Jeff  | (555)555-5555 |
      | smith@ap.com | pass2    | Smith | (555)555-5555 |
    Given the following manager exists in the system (p5)
      | email          | password |
      | manager@ap.com | manager  |
    Given the following asset types exist in the system (p5)
      | name | expectedLifeSpan |
      | lamp |             1800 |
      | bed  |             5000 |
    Given the following assets exist in the system (p5)
      | assetNumber | type | purchaseDate | floorNumber | roomNumber |
      |           1 | lamp |   2022-03-20 |           9 |         23 |
      |           2 | bed  |   2010-01-30 |          10 |         35 |
    Given the following tickets exist in the system (p5)
      | id | ticketRaiser   | raisedOnDate | description                   | assetNumber |
      |  1 | manager@ap.com |   2023-07-20 | This is a dummy description 1 |           2 |
      |  2 | smith@ap.com   |   2023-07-10 | This is a dummy description 2 |           1 |
    Given the following ticket images exist in the system (p5)
      | imageUrl                   | ticketId |
      | https://imageurl.com/i.jpg |        1 |
      | http://thisimage.com/1.png |        1 |

  Scenario Outline: Successfully add ticket image to a ticket
    When hotel staff adds an image with url "<imageUrl>" to the ticket with id "<ticketId>" (p5)
    Then the number of images in the system shall be "3" (p5)
    Then the number of images for ticket with id "<ticketId>" in the system shall be "<numberOfImagesOfTicket>" (p5)
    Then the ticket with id "<ticketId>" shall have an image with url "<imageUrl>" (p5)

    Examples: 
      | imageUrl                   | ticketId | numberOfImagesOfTicket |
      | https://imageurl.com/j.jpg |        1 |                      3 |
      | http://thisimage.com/2.png |        2 |                      1 |

  Scenario Outline: Successfully add the same ticket image to a second ticket
    When hotel staff adds an image with url "<imageUrl>" to the ticket with id "<ticketId>" (p5)
    Then the number of images in the system shall be "3" (p5)
    Then the number of images for ticket with id "<ticketId>" in the system shall be "<numberOfImagesOfTicket>" (p5)
    Then the ticket with id "<ticketId>" shall have an image with url "<imageUrl>" (p5)

    Examples: 
      | imageUrl                   | ticketId | numberOfImagesOfTicket |
      | http://thisimage.com/1.png |        2 |                      1 |

  Scenario Outline: Unsuccessfully add ticket image to a ticket
    When hotel staff adds an image with url "<imageUrl>" to the ticket with id "<ticketId>" (p5)
    Then the number of images in the system shall be "2" (p5)
    Then the following ticket images shall exist in the system (p5)
      | imageUrl                   | ticketId |
      | https://imageurl.com/i.jpg |        1 |
      | http://thisimage.com/1.png |        1 |
    Then the system shall raise the error "<error>" (p5)

    Examples: 
      | imageUrl                   | ticketId | error                                         |
      | thisisnotanurl             |        1 | Image URL must start with http:// or https:// |
      | htps://image.com/3.png     |        2 | Image URL must start with http:// or https:// |
      |                            |        2 | Image URL cannot be empty                     |
      | http://thisimage.com/3.png |        3 | Ticket does not exist                         |
      | https://imageurl.com/i.jpg |        1 | Image already exists for the ticket           |
