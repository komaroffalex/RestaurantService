#ifndef RESTAURANTSERVICE_TABLE_H
#define RESTAURANTSERVICE_TABLE_H

#include <string>

class Table {  
    enum Location { CENTER, EAST, WEST, NORTH, SOUTH };
public:
    Table() {};

    int id() const { return mId; };

    void setId(int id) { mId = id; };

    int seats() const { return mSeats; };

    void setSeats(int seats) { mSeats = seats; };

    Location location() const { return mLocation; };

    void setLocation(Location location) { mLocation = location; };

    float cost() const { return mCost; };

    void setCost(float cost) { mCost = cost; };    

private:
    int mId;

    int mSeats;

    Location mLocation;

    float mCost;
};

#endif //RESTAURANTSERVICE_TABLE_H
