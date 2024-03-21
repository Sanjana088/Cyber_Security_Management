#include "VulZero.h"
#include <QtWidgets/QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    VulZero w;
    w.show();
    return a.exec();
}
