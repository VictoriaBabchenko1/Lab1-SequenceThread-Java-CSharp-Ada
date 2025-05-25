with Ada.Text_IO; use Ada.Text_IO;
with Ada.Calendar; use Ada.Calendar;

procedure Thread_Demo is
   task type Sequence_Task(Id : Integer; Step : Integer);

   task body Sequence_Task is
      Sum : Integer := 0;
      Count : Integer := 0;
      Current : Integer := 0;
      Start_Time : Time := Clock;
   begin
      loop
         exit when Clock - Start_Time > Duration(3.0);
         Sum := Sum + Current;
         Current := Current + Step;
         Count := Count + 1;
         delay 0.01;
      end loop;
      Put_Line("Thread " & Integer'Image(Id) & ": Sum = " & Integer'Image(Sum) & ", Count = " & Integer'Image(Count));
   end Sequence_Task;

   T1 : Sequence_Task(1, 1);
   T2 : Sequence_Task(2, 2);
   T3 : Sequence_Task(3, 3);

begin
   null;
end Thread_Demo;
