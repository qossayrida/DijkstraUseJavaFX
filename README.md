# GIS Route Finder using Dijkstra's Algorithm

This project implements an optimized version of Dijkstra's shortest path algorithm for geographic information systems (GIS), such as those used in MapQuest and GPS-based navigation systems. The program computes the shortest route between cities on a map based on Euclidean distances and displays the route on a visual map interface.

## Features

1. **Interactive Map Interface**: Select cities using mouse and keyboard inputs.
2. **Shortest Path Calculation**: Finds and displays the lowest-cost route between two selected cities.
3. **Dynamic Route Display**: The route is shown visually on the map.
4. **Optimized for Large Maps**: Efficiently handles maps with at least 50 cities and thousands of edges.
5. **Map Preprocessing**: Speeds up repeated shortest path queries by reusing previous computations.

## File Format

The map is represented in a text file with the following structure:

    Number of vertices (cities)  
    Number of edges (roads) 
    City1 Latitude1 Longitude1  0   (0 means this road cross)  
    City2 Latitude2 Longitude2  1   (1 means this city) 
    ...  
    Edge1_City1 Edge1_City2  
    Edge2_City1 Edge2_City2  
    ...  

Example:

    6
    9
    City1 31.52583 34.45250    1
    City2 31.53389 35.09944    1
    City3 31.52972 34.48139    1
    City4 31.35611 34.30139    1
    City5 31.90000 35.20417    1
    cross1 31.70639 35.20167   0 
    City1 City2
    City1 City4
    City2 City3
    City2 City5
    City3 City5
    City3 City4
    City3 cross1
    City4 cross1
    City5 cross1

## How to use
Upon launching the application, you will be greeted with the Welcome Screen. Here you can choose to 

  ![image](https://github.com/qossayrida/QossayRida/assets/59481839/a02432a6-088d-4441-a8e1-2a55a3148dab)

- **Choose Data manuale:**

    Selecting Upload data from xlsx file will open a file dialog where you can choose an xlsx file from your computer that contains the data ,then choose the image for map
  
    ![image](https://github.com/qossayrida/QossayRida/assets/59481839/bbe52e4d-b9a7-4291-8a65-b44ac4f61b90)

- **Gaza Map:** 

    You'll be prompted to choose the source city from ComboBox or click on it in the map, and the same for the destination city, then you click at Find button the shortest path will found  

    ![image](https://github.com/qossayrida/QossayRida/assets/59481839/9ea10e13-e9d9-4867-b612-d5eb0f58c344)

    ![image](https://github.com/qossayrida/QossayRida/assets/59481839/97888a91-226b-4845-9903-0c281c0c9258)


- **Palestine Map:** 

    You'll be prompted to choose the source city from ComboBox or click on it in the map, and the same for the destination city, then you click at Find button the shortest path will found  

    ![image](https://github.com/qossayrida/QossayRida/assets/59481839/22e2ceef-bda3-42e0-993c-96de6defe3fb)





## 🔗 Links

[![facebook](https://img.shields.io/badge/facebook-0077B5?style=for-the-badge&logo=facebook&logoColor=white)](https://www.facebook.com/qossay.rida?mibextid=2JQ9oc)

[![Whatsapp](https://img.shields.io/badge/Whatsapp-25D366?style=for-the-badge&logo=Whatsapp&logoColor=white)](https://wa.me/+972598592423)

[![linkedin](https://img.shields.io/badge/linkedin-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/qossay-rida-3aa3b81a1?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app )

[![twitter](https://img.shields.io/badge/twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/qossayrida)
