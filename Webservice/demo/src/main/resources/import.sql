-- import.sql

-- placeholder data to be inserted to the application during bootstrap proccess user for testing purposes
insert into calls(id, call_status, num_caller, num_callee, type, time_start, time_end) values (1,'ENDED','232111111', '215222222', 'OUTBOUND', '2020-01-07 01:00:20', '2020-01-07 01:02:20');
insert into calls(id, call_status, num_caller, num_callee, type, time_start, time_end) values (2,'ENDED','215222222', '232111111', 'INBOUND', '2020-01-07 02:04:20', '2020-01-07 02:05:20');
insert into calls(id, call_status, num_caller, num_callee, type, time_start, time_end) values (3,'ENDED','215222222', '333111222', 'INBOUND', '2020-01-07 03:25:00', '2020-01-07 03:40:27');
insert into calls(id, call_status, num_caller, num_callee, type, time_start, time_end) values (4,'ENDED','333111222', '215222222', 'OUTBOUND', '2020-01-07 16:54:20', '2020-01-07 17:05:20');

insert into calls(id, call_status, num_caller, num_callee, type, time_start, time_end) values (5,'ENDED','444555222', '333111222', 'OUTBOUND', '2020-01-06 03:25:00', '2020-01-06 03:40:27');
insert into calls(id, call_status, num_caller, num_callee, type, time_start, time_end) values (6,'ENDED','444555222', '215222222', 'OUTBOUND', '2020-01-06 16:54:20', '2020-01-06 17:05:20');

