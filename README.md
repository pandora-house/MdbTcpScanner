A simple program "Modbus Tcp Scanner" created for such cases when protocol modbus tcp not defined.

The program will iterates over the marked combinations.

In case when IP or Port not defined is enough to enter Ip using hyphen, for example 192.168.1-255.1-255, and program will iterates over this combinations. The same for Port. But here the problem: all communication going trough socket and if some ports is open on the your computer, program will try to connect to this port and trow exception "Read time out". So if you want to find valid socket connection your Ip or Port range shoud to be narrow, for example Ip 192.168.1.1-10 Port 500-505.
