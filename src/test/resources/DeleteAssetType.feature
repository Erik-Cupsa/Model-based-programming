Feature: Delete Asset Type (p4)
As the manager, I want to delete an existing asset type in the system so that it is no longer available.

  Background: 
    Given the following asset types exist in the system (p4)
      | name   | expectedLifeSpan |
      | lamp   |             1800 |
      | pillow |              700 |
      | bed    |             5000 |

  Scenario: Successfully delete an asset type
    When the manager attempts to delete an asset type in the system with name "lamp" (p4)
    Then the number of asset types in the system shall be "2" (p4)
    Then the following asset types shall exist in the system (p4)
      | name   | expectedLifeSpan |
      | pillow |              700 |
      | bed    |             5000 |

  Scenario Outline: Successfully delete an asset type that does not exist in the system
    When the manager attempts to delete an asset type in the system with name "<name>" (p4)
    Then the number of asset types in the system shall be "3" (p4)
    Then the following asset types shall exist in the system (p4)
      | name   | expectedLifeSpan |
      | lamp   |             1800 |
      | pillow |              700 |
      | bed    |             5000 |

    Examples: 
      | name       |
      | table lamp |
      | desk       |
