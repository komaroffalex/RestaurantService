#ifndef RESTAURANTSERVICE_ADMINISTRATOR_H
#define RESTAURANTSERVICE_ADMINISTRATOR_H

#include <string>

#include "User.h"

class Administrator : public User {
public:
    Administrator() : User() {};  

    Administrator(int id, std::string username, std::string password, std::string firstName, std::string lastName, std::string eMail, std::string messenger, std::string phoneNumber) 
        : User(id, username, password, firstName, lastName, eMail, messenger, phoneNumber) 
    {};
};

#endif //RESTAURANTSERVICE_ADMINISTRATOR_H