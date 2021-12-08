**Personal Banking**

**Modules**

1. List Products
2. Display Product Detail

**Architecture Layer Diagram**

<img src="media/Clean_Architecture_diagram.png"/>

**Data and Domain** - Core layers less prone to changes. As these two layers are abstract can be plugged to other platforms.
    
    1. Domain - Models 
    2. Data - Abstract data source
    
**Framework** - It provides the implementation for the data layer and interacts with the platform (Android SDK).   

    1. Endpoints specification
    2. Data source implementation
    3. View Models 
    
**Presentation** - This layer more prone to changes so it has concrete implementation of UI. It's only task is display the data in UI.

    1. Activities, Fragments, Views etc
    
    