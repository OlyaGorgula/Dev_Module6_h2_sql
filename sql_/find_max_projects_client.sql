select T.name, T.project_count
from (select client.*, count(*) as project_count
    from client, project
    where client.id = project.client_id
    group by client.id) as T
where project_count IN
        (select count(*) as project_count
         from project
         group by client_id
         order by project_count desc
         limit 1);