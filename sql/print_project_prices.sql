SELECT T.name, SUM(T.price) as price
FROM (
    SELECT project_worker.project_id, project.name, DATEDIFF(month, project.start_date, project.finish_date) * worker.salary as price
    FROM project_worker
    RIGHT JOIN project ON project_worker.project_id = project.id
    LEFT JOIN worker ON project_worker.worker_id = worker.id
) T
GROUP BY T.project_id
;