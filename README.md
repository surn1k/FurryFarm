# Differential Practicum

**Student:** Gilvanov Ruslan, BS20-01
**Varianrt**: 20


## Introduction
As the initial value problem may be very hard for solving in automatic way, in such situations we can use a special methods of approximation. We call that methods numeric and in that assignment we will cover the following of them:
- Euler method
- Improved Euler method
- Runge-Kutta method
    
## Goals:
 1. Analyse the given task, formulate strategy of solution 
 2. Analyticaly solve the Initial value problem, using methods that was introduced during Differential equations course classes
 3. Plan the structure of the future application
 4. Build 


## Analytical Solution
![](https://i.imgur.com/PY9LlwS.jpg)
![](https://i.imgur.com/WtRb7ZU.jpg)


## Computational Practicum

For a given problem an automatic solution application was developed.

### Initial
The initial values are already put inside the program and corresponidng graphics of aproximation and local truncation errors of 3 given methods were built

Data for building LTE and Aproximation graphics:
- x0 - initial value of x
- y0 - initial value of y
- X - the upper bound of x values
- h - aproximation step

Data for building GTE graphics:
- x0 - initial value of x
- y - initial value of y
- X - the upper bound of x values
- N - number of grid steps

Data to be presented on graphics:
- 3 aproximation plots, each for corresponding method
- 3 local truncation error plots
- 3 global truncation error plots

### Results 

- Test1:
    * Initial values:
        x0 = 1
        y0 = 1
        X = 6
        h = 0.6
        N = 10
    * Program output
    !["Aproximation graphs"](https://i.imgur.com/BOUHDHB.png)
    !["Local error graphs"](https://i.imgur.com/zxkmx1I.png)
    ![Global errors graphs](https://i.imgur.com/mx4xrO8.png)

    * Comments: As we can see Ringe-Kutta graphic have a very high precision and very close exact solution. Opposite to that, Euler method demostrates high error rate

- Test2:
    * Initial values:
        x0 = 1
        y0 = 1
        X = 6
        h = 0.3
        N = 100
    * Program output
    ![](https://i.imgur.com/Grk9ViH.png)
    ![](https://i.imgur.com/qhpSeiQ.png)
    ![](https://i.imgur.com/HmDm8B0.png)

 

    * Comments: the reduction of h parameter have increased the precision of aproximate function, now we can see that with a realy small step h, Runge-Kutta function are almost shadowed the exact solution and improved Euler graphic is going to be very close to it. Also we have increased number of grid steps to observe gte graphs in more accurate way. So far we can see that the depency errors from N is definetly quadratic monitinically decreasing function 

- Test 3
    * Initial values:
        x0 = 1
        y0 = 1
        X = 3
        h = 0.3
        N = 100
    * Program output:
        ![](https://i.imgur.com/CCUbOt0.png)
        ![](https://i.imgur.com/JbmVLBx.png)
        
    * Comments: the increased h have strongly decresead the precision of aproximation graphics, as we can see, euler graphic is more  than others depends on h.


## Code overview
1. For calculation code uses 3 classes of Numeric methods: Euler, ImprovedEuler, Runge-Kutta which are dependent on abstract class NumericMethod
2. All the numeric classes uses Function. static class with all function sets needed for calculations
3. the drawing of graphs is related to class Drawer which will configure and calculate all the needed data for the generating plots

![](https://i.imgur.com/rGV8alr.png)


## Conclusion
After all checks, we can surely say that Ringe-Kutta method demonstrates a high precision rate. Also all global errors functions show the quadratic dependency from number of steps. Also we can see, that the precision is depending from number of steps, that is defined by h, x0 and X paramenters
