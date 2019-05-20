#ifndef RESTAURANTSERVICE_WORKER_H
#define RESTAURANTSERVICE_WORKER_H

#include <string>

#include "User.h"

class Worker : public User {
public:
    Worker() : User() {};

    Worker(int id, std::string username, std::string password, std::string firstName, std::string lastName, std::string eMail, std::string messenger, std::string phoneNumber, std::string position)
        : User(id, username, password, firstName, lastName, eMail, messenger, phoneNumber), mPosition(position)
    {};

    std::string position() const { return mPosition; };

    void setPosition(std::string position) { mPosition = position; };

private:
    std::string mPosition;
};

#endif //RESTAURANTSERVICE_WORKER_H
