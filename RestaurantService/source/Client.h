#ifndef RESTAURANTSERVICE_CLIENT_H
#define RESTAURANTSERVICE_CLIENT_H

#include <string>

#include "User.h"

class Client : public User {
public:
    Client() : User() {};

    Client(int id, std::string username, std::string password, std::string firstName, std::string lastName, std::string eMail, std::string messenger, std::string phoneNumber)
        : User(id, username, password, firstName, lastName, eMail, messenger, phoneNumber)
    {};
};

#endif //RESTAURANTSERVICE_CLIENT_H
