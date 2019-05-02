#ifndef RESTAURANTSERVICE_HALLPLAN_H
#define RESTAURANTSERVICE_HALLPLAN_H

#include <string>
#include <vector>

#include "Table.h"

class HallPlan {
    enum HallStatus { OCCUPIED, HAS_FREE_TABLES };
public:
    HallPlan() {};

    int id() const { return mId; };

    void setId(int id) { mId = id; };

    int capacity() const { return mCapacity; };

    void setCapacity(int capacity) { mCapacity = capacity; };

    int status() const { return mStatus; };

    void setStatus(HallStatus status) { mStatus = status; };

    std::vector<Table> tables() const { return mTables; };

    void setTables(std::vector<Table> tables) { mTables = tables; };

private:
    int mId;

    int mCapacity;

    HallStatus mStatus;

    std::vector<Table> mTables;
};

#endif //RESTAURANTSERVICE_HALLPLAN_H