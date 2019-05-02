#ifndef RESTAURANTSERVICE_ADMINISTRATOR_H
#define RESTAURANTSERVICE_ADMINISTRATOR_H

#include <string>

class Administrator {
public:
    Administrator() {};

    int id() const { return mId; };

    void setId(int id) { mId = id; };

    std::string username() const { return mUsername; };

    void setUsername(std::string username) { mUsername = username; };

    std::string password() const { return mPassword; };

    void setPassword(std::string password) { mPassword = password; };

    std::string firstName() const { return mFirstName; };

    void setFirstName(std::string firstName) { mFirstName = firstName; };

    std::string lastName() const { return mLastName; };

    void setLastName(std::string lastName) { mLastName = lastName; };

    std::string eMail() const { return mEMail; };

    void setEMail(std::string eMail) { mEMail = eMail; };

    std::string messenger() const { return mMessenger; };

    void setMessenger(std::string messenger) { mMessenger = messenger; };

    std::string phoneNumber() const { return mMessenger; };

    void setPhoneNumber(std::string phoneNumber) { mPhoneNumber = phoneNumber; };

private:
    int mId;

    std::string mUsername;

    std::string mPassword;

    std::string mFirstName;

    std::string mLastName;

    std::string mEMail;

    std::string mMessenger;

    std::string mPhoneNumber;
};

#endif //RESTAURANTSERVICE_ADMINISTRATOR_H