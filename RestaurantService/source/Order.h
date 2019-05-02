#ifndef RESTAURANTSERVICE_ORDER_H
#define RESTAURANTSERVICE_ORDER_H

#include <string>
#include <vector>

class Order {
    enum OrderStatus { READY, PREPARING, DENIED, PENDING };
public:
    Order() {};

    int id() const { return mId; };

    void setId(int id) { mId = id; };

    std::vector<std::string> courses() const { return mCourses; };

    void setCourses(std::vector<std::string> &courses) { mCourses = courses; };

    std::string address() const { return mAddress; };

    void setAddress(std::string address) { mAddress = address; };

    OrderStatus orderStatus() const { return mStatus; };

    void setStatus(OrderStatus status) { mStatus = status; };

    float cost() const { return mCost; };

    void setCost(float cost) { mCost = cost; };

    std::string deliveryTime() const { return mDeliveryTime; };

    void setDeliveryTime(std::string deliveryTime) { mDeliveryTime = deliveryTime; };

private:
    int mId;

    std::vector<std::string> mCourses;

    std::string mAddress;

    OrderStatus mStatus;

    float mCost;

    std::string mDeliveryTime;
};

#endif //RESTAURANTSERVICE_ORDER_H
