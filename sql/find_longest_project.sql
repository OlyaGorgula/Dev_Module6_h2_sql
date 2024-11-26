select T.name, T.monthCount
from (select name, DATEDIFF(month, start_date, finish_date) as monthCount
    from project
    ) as T
where T.monthCount IN (select MAX(DATEDIFF(month, project.start_date, project.finish_date)) from project);
