// RestaurantService.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
//

#include <iostream>

#include "source/Worker.h"

int main()
{
    Worker sample(92, "sample", "sample", "sample", "sample", "sample", "sample", "sample", "sample");
    std::cout << sample.password()<<std::endl;
    std::cout << "Hello World!\n"; 
}
