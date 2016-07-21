#XMLSwing
This is a java liburary that converts XML code to swing code

##TODO
1. go through the swing attributes to make sure it can add them all
3. make the XML Reader more robust ie can handle quotes and spaces properly

##Features
can add Swing elements based off the name of the xml tag ie <JLabel ... ></JLabel>
can add attributes with the method name eg <JLabel setText=text></JLabel> this only works for methods that take an Integer String or have no parameters
there are some special attributes like
1. width: sets the width and keeps the height the same
2. height: sets the height keeps the width the same
3. layout: to set the layout
4. setRows and setColumns: this is for the grid layout in a JPanel
