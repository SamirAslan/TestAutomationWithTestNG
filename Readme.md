# Test Automation Project

## Description
This project is a test automation suite written in Java using tools like Selenium, TestNG/JUnit, and Maven. It is designed to automate testing for web applications and APIs.

---

## Prerequisites
Before you begin, ensure you have the following installed on your system:
1. **Java Development Kit (JDK)**
2. **Apache Maven**
3. An IDE (e.g., IntelliJ IDEA, Eclipse) for editing and running the project (optional).
4. **Google Chrome**
>![img.png](src%2Fmain%2Fresources%2FreadmeFoto%2Fimg.png)



---

## Installation
1. Open the project in IntelliJ. 
>Project structure
> 
> ![img_1.png](src%2Fmain%2Fresources%2FreadmeFoto%2Fimg_1.png)

2. Install the dependencies:
    ```bash
    mvn clean install
    ```

3. Update the configuration:
    - Edit the `configuration.properties` file located in the `src/test/resources` folder to provide the necessary test data (e.g., URLs, data, etc.).
>
---![img_2.png](src%2Fmain%2Fresources%2FreadmeFoto%2Fimg_2.png)
>
>If the ``headless`` parameter in the ``configuration.properties`` file is ``true``, the scenario is run with the browser closed, if ``false``, the scenario is run with the browser open.
>Other data are the data used in the scenarios.

## Running the Tests
### 1. Run Tests

1. The scenarios are in the ``src/test/tests/AmazonTest``class.
2. The scenarios are named ``TC_00x``
3. All task scenarios are written in ``AmazonTest`` class

>![img_3.png](src%2Fmain%2Fresources%2FreadmeFoto%2Fimg_3.png)